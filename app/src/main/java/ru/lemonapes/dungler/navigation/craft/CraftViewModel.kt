package ru.lemonapes.dungler.navigation.craft

import ru.lemonapes.dungler.parent_store.ViewModelStore
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.navigation.craft.mappers.CraftItemResponseMapper
import ru.lemonapes.dungler.network.endpoints.loadCraftItems

interface CraftAction {
    fun actionStart()
    fun actionError(throwable: Throwable)
    fun actionClearError()
}

class CraftViewModel :
    ViewModelStore<CraftViewState>(CraftViewState.getEmpty()), CraftAction {

    override val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        actionError(throwable)
    }

    init {
        actionStart()
    }

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            val (items, reagents) = CraftItemResponseMapper(loadCraftItems())
            updateState { state ->
                state.copy(
                    items = items,
                    reagents = reagents
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