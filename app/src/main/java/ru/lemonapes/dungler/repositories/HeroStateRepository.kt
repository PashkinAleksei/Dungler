package ru.lemonapes.dungler.repositories

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.onTimeout
import kotlinx.coroutines.selects.select
import ru.lemonapes.dungler.Utils.Companion.log
import ru.lemonapes.dungler.hero_state.DungeonState
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.hero_state.HeroState.Companion.ACTION_CHECK_TICK_TIME
import ru.lemonapes.dungler.mappers.HeroStateResponseMapper
import ru.lemonapes.dungler.network.endpoints.getHeroState
import ru.lemonapes.dungler.stores.HeroStateStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroStateRepository @Inject constructor(
    private val heroStateStore: HeroStateStore,
) {

    private fun getPollingExceptionHandler(coroutineScope: CoroutineScope) = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        startPolling(coroutineScope = coroutineScope, isRetry = true)
    }

    val heroStateFlow
        get() = heroStateStore.heroStateFlow

    private val pollingResetChannel = Channel<ChannelAction>(Channel.CONFLATED)


    private var pollingJob: Job? = null
    private var actionCountingJob: Job? = null

    private var polingRetryCounter = 0

    @OptIn(ExperimentalCoroutinesApi::class)
    fun startPolling(coroutineScope: CoroutineScope, isRetry: Boolean = false) {
        log("startPolling")
        pollingJob?.cancel()
        pollingJob = coroutineScope.launch(Dispatchers.IO + getPollingExceptionHandler(coroutineScope)) {
            if (isRetry) {
                polingRetryCounter++
                if (polingRetryCounter > 3) {
                    delay(POLLING_RETRY_DELAY)
                }
            }
            while (isActive) {
                fetchHeroState(coroutineScope)
                polingRetryCounter = 0

                //make new polling after POLLING_INTERVAL,
                //but have opportunity to make it immediately using resetChannel
                select {
                    onTimeout(POLLING_INTERVAL) { }
                    pollingResetChannel.onReceive { action ->
                        when (action) {
                            ChannelAction.RESET -> {}
                            ChannelAction.DELAY -> delay(POLLING_INTERVAL)
                        }
                    }
                }
            }
        }
    }

    private fun resetPolling() {
        pollingResetChannel.trySend(ChannelAction.RESET)
    }

    private fun delayPolling() {
        pollingResetChannel.trySend(ChannelAction.DELAY)
    }

    fun stopPolling() {
        log("stopPolling")
        pollingJob?.cancel()
    }

    private suspend fun fetchHeroState(coroutineScope: CoroutineScope) {
        log("start fetching HeroState")
        heroStateStore.heroState = HeroStateResponseMapper(getHeroState())
        log("HeroState fetched")
        startActionsCalculation(coroutineScope)
    }

    fun heroInDungeonError() {
        heroStateStore.heroState = heroStateStore.heroState.copy(
            isLoading = true,
            dungeonState = DungeonState()
        )
        resetPolling()
    }

    fun setNewHeroState(coroutineScope: CoroutineScope, newHeroState: HeroState) {
        heroStateStore.heroState = newHeroState
        delayPolling()
        startActionsCalculation(coroutineScope)
    }

    private fun startActionsCalculation(coroutineScope: CoroutineScope) {
        actionCountingJob?.cancel()
        actionCountingJob = coroutineScope.launch(Dispatchers.IO) {
            while (isActive) {
                calculateActions()
                delay(ACTION_CHECK_TICK_TIME)
            }
        }
    }

    fun stopActionsCalculation() {
        actionCountingJob?.cancel()
    }

    private fun calculateActions() {
        runCatching {
            heroStateStore.updateState { heroState ->
                heroState.calculateActionsRecursiveAndGet()
            }
        }.onFailure {
            stopActionsCalculation()
        }
    }

    companion object {
        const val POLLING_INTERVAL = 15000L
        const val POLLING_RETRY_DELAY = 5000L
    }

    private enum class ChannelAction {
        RESET, DELAY
    }
}
