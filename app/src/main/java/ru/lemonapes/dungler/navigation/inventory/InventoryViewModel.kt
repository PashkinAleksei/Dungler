package ru.lemonapes.dungler.navigation.inventory

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.mappers.InventoryResponseMapper
import ru.lemonapes.dungler.network.endpoints.loadInventory
import ru.lemonapes.dungler.parent_store.ViewModelStore

interface InventoryAction {
    fun actionStart()
    fun actionError(throwable: Throwable)
    fun actionClearError()
}

class InventoryViewModel : ViewModelStore<InventoryState>(InventoryState.EMPTY), InventoryAction {

    override val ceh = CoroutineExceptionHandler { _, throwable ->
        actionError(throwable)
    }

    init {
        actionStart()
    }

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            val (gears, reagents) = InventoryResponseMapper(loadInventory())

            updateState { state ->
                state.copy(
                    gears = gears,
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