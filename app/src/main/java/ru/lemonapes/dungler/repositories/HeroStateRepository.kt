package ru.lemonapes.dungler.repositories

import android.icu.util.Calendar
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
import ru.lemonapes.dungler.hero_state.Action
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

    val heroStateFlow
        get() = heroStateStore.heroStateFlow

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        log("$throwable")
        throwable.printStackTrace()
    }

    private fun getPollingExceptionHandler(coroutineScope: CoroutineScope) = CoroutineExceptionHandler { _, throwable ->
        log("$throwable")
        throwable.printStackTrace()
        startPolling(coroutineScope = coroutineScope, isRetry = true)
    }

    private var pollingResetChannel: Channel<ChannelAction>? = null

    @Volatile
    private var isActionCountingActive = true


    private var pollingJob: Job? = null
    private var actionCalculationJob: Job? = null

    private var polingRetryCounter = 0

    @OptIn(ExperimentalCoroutinesApi::class)
    fun startPolling(coroutineScope: CoroutineScope, isRetry: Boolean = false) {
        //log("startPolling coroutineScope")
        pollingResetChannel = Channel(Channel.CONFLATED)
        pollingJob?.cancel()
        pollingJob = coroutineScope.launch(Dispatchers.IO + getPollingExceptionHandler(coroutineScope)) {
            if (isRetry) {
                polingRetryCounter++
                if (polingRetryCounter > 3) {
                    delay(POLLING_RETRY_DELAY)
                }
            }
            while (isActive) {
                fetchHeroState()
                polingRetryCounter = 0

                //make new polling after POLLING_INTERVAL,
                //but have opportunity to make it immediately using resetChannel
                select {
                    onTimeout(POLLING_INTERVAL) { }
                    pollingResetChannel?.onReceive { action ->
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
        pollingResetChannel?.trySend(ChannelAction.RESET)
    }

    private fun delayPolling() {
        pollingResetChannel?.trySend(ChannelAction.DELAY)
    }

    fun stopPolling() {
        //log("stopPolling")
        pollingJob?.cancel()
    }

    private suspend fun fetchHeroState() {
        //log("start fetching HeroState")
        heroStateStore.heroState = HeroStateResponseMapper(getHeroState())
        resumeActionsCalculation()
        log("HeroState fetched ${heroStateStore.heroState}")
        resumeActionsCalculation()
    }

    fun heroInDungeonError() {
        heroStateStore.heroState = heroStateStore.heroState.copy(
            isLoading = true,
            dungeonState = DungeonState()
        )
        resetPolling()
    }

    fun setNewHeroState(newHeroState: HeroState) {
        log("setNewHeroState $newHeroState")
        heroStateStore.heroState = newHeroState
        delayPolling()
        resumeActionsCalculation()
    }

    //-----------------------------------------------------------------------------------------------------------------
    fun startActionsCalculation(coroutineScope: CoroutineScope) {
        resumeActionsCalculation()
        actionCalculationJob?.cancel()
        actionCalculationJob = coroutineScope.launch(Dispatchers.Default + ceh) {
            while (isActive) {
                if (isActionCountingActive) {
                    calculateActions()
                    //log("calculateActions ended ${actionCalculationJob?.isActive} $isActive\n")
                }
                delay(ACTION_CHECK_TICK_TIME)
                //log("delay(ACTION_CHECK_TICK_TIME)\n")
            }
        }
    }

    private fun pauseActionsCalculation() {
        //log("pauseActionsCalculation")
        isActionCountingActive = false
    }

    private fun resumeActionsCalculation() {
        //log("resumeActionsCalculation")
        isActionCountingActive = true
    }

    fun stopActionsCalculation() {
        //log("stopActionCalculation")
        actionCalculationJob?.cancel()
    }

    private fun calculateActions() {
        heroStateStore.updateState { heroState ->
            when {
                heroState.nextCalcTime == 0L ||
                        heroState.isLoading ||
                        heroState.actions.firstOrNull() is Action.ActualStateAction -> {
                    //log("1_heroState")
                    pauseActionsCalculation()
                    heroState
                }

                heroState.actions.isEmpty() || heroState.actions.firstOrNull() is Action.ActualStateAction -> {
                    //log("2_$heroState")
                    pauseActionsCalculation()
                    resetPolling()
                    heroState
                }

                Calendar.getInstance().timeInMillis > heroState.nextCalcTime -> {
                    //log("3_$heroState")
                    heroState.calculateActionsRecursiveAndGet()
                }

                Calendar.getInstance().timeInMillis <= heroState.nextCalcTime -> {
                    //log("4_$heroState")
                    heroState
                }

                else -> {
                    //log("5_$heroState")
                    heroState
                }
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    companion object {
        const val POLLING_INTERVAL = 15000L
        const val POLLING_RETRY_DELAY = 5000L
    }

    private enum class ChannelAction {
        RESET, DELAY
    }
}
