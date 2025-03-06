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
import ru.lemonapes.dungler.mappers.HeroStateMapper
import ru.lemonapes.dungler.network.endpoints.getHeroState
import ru.lemonapes.dungler.stores.HeroStateStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroStateRepository @Inject constructor(
    private val heroStateStore: HeroStateStore,
) {

    private fun getExceptionHandler(coroutineScope: CoroutineScope) = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        startPolling(coroutineScope = coroutineScope, isRetry = true)
    }

    val heroStateFlow
        get() = heroStateStore.heroStateFlow

    private val resetChannel = Channel<ChannelAction>(Channel.CONFLATED)


    private var pollingJob: Job? = null

    private var polingRetryCounter = 0

    @OptIn(ExperimentalCoroutinesApi::class)
    fun startPolling(coroutineScope: CoroutineScope, isRetry: Boolean = false) {
        log("startPolling")
        pollingJob?.cancel()
        pollingJob = coroutineScope.launch(Dispatchers.IO + getExceptionHandler(coroutineScope)) {
            if (isRetry) {
                polingRetryCounter++
                if (polingRetryCounter > 3) {
                    delay(POLLING_RETRY_DELAY)
                }
            }
            while (isActive) {
                fetchHeroState()
                polingRetryCounter = 0

                //make polling after POLLING_INTERVAL,
                //but have opportunity to make it immediately using resetChannel
                select {
                    onTimeout(POLLING_INTERVAL) { }
                    resetChannel.onReceive { action ->
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
        resetChannel.trySend(ChannelAction.RESET)
    }

    private fun delayPolling() {
        resetChannel.trySend(ChannelAction.DELAY)
    }

    fun stopPolling() {
        log("stopPolling")
        pollingJob?.cancel()
    }

    private suspend fun fetchHeroState() {
        log("start fetching HeroState")
        heroStateStore.heroState = HeroStateMapper(getHeroState())
        log("HeroState fetched")
    }

    fun heroInDungeonError() {
        heroStateStore.heroState = heroStateStore.heroState.copy(
            isLoading = true,
            dungeonState = DungeonState()
        )
        resetPolling()
    }

    fun setNewHeroState(newHeroState: HeroState) {
        heroStateStore.heroState = newHeroState
        delayPolling()
    }

    companion object {
        const val POLLING_INTERVAL = 15000L
        const val POLLING_RETRY_DELAY = 5000L
    }

    private enum class ChannelAction {
        RESET, DELAY
    }
}
