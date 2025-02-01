package ru.lemonapes.dungler.navigation.craft

import ru.lemonapes.dungler.parent_store.ViewModelStore
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.navigation.ktor.loadCraftItems

interface CraftAction {
    fun actionStart()
    fun actionError(throwable: Throwable)
    fun actionClearError()
}

class CraftViewModel() :
    ViewModelStore<CraftViewState>(CraftViewState.getEmpty()), CraftAction {

    override val ceh = SupervisorJob() + CoroutineExceptionHandler { coroutineContext, throwable ->
        actionError(throwable)
    }

    init {
        actionStart()
    }

    override fun actionStart() = withActualState {
        launch(ceh) {
            val craftItemsResponse = loadCraftItems()
            updateState { state ->
                state.copy(
                    items = craftItemsResponse.items,
                    reagents = craftItemsResponse.reagents
                )
            }
        }
    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        oldState.copy(error = throwable)
    }

    override fun actionClearError() = updateState { oldState ->
        oldState.copy(error = null)
    }
}