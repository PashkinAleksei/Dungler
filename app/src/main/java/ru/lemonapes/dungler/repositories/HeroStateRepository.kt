package ru.lemonapes.dungler.repositories

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.onTimeout
import kotlinx.coroutines.selects.select
import ru.lemonapes.dungler.mappers.HeroStateMapper
import ru.lemonapes.dungler.network.endpoints.getHeroState
import ru.lemonapes.dungler.stores.HeroStateStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroStateRepository @Inject constructor(private val heroStateStore: HeroStateStore) {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    val heroStateFlow
        get() = heroStateStore.heroStateFlow

    private val resetChannel = Channel<Unit>(Channel.CONFLATED)
    private var pollingJob: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun startPolling(coroutineScope: CoroutineScope) {
        pollingJob?.cancel()
        coroutineScope.apply {
            pollingJob = launch(Dispatchers.IO + exceptionHandler) {
                while (isActive) {
                    doAction()

                    //make polling after POLLING_INTERVAL,
                    //but have opportunity to make it immediately using resetChannel
                    select {
                        onTimeout(POLLING_INTERVAL) { }
                        resetChannel.onReceive { }
                    }
                }
            }
        }
    }

    fun resetPolling(coroutineScope: CoroutineScope) {
        resetChannel.trySend(Unit)
    }

    fun stopPolling() {
        pollingJob?.cancel()
    }

    private suspend fun fetchHeroState() {
        heroStateStore.heroState = HeroStateMapper(getHeroState())
    }

    private suspend fun doAction() {
        coroutineScope {
            try {
                fetchHeroState()
            } catch (e: Exception) {
                exceptionHandler.handleException(context = coroutineContext, exception = e)
            }
        }
    }

    companion object {
        const val POLLING_INTERVAL = 15000L
    }
}
