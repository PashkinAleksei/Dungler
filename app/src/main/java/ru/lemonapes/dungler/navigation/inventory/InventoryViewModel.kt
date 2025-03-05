package ru.lemonapes.dungler.navigation.inventory

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.mappers.InventoryResponseMapper
import ru.lemonapes.dungler.network.endpoints.getInventory
import ru.lemonapes.dungler.parent_store.ViewModelAction
import ru.lemonapes.dungler.parent_store.ViewModelStore
import javax.inject.Inject

interface InventoryAction : ViewModelAction

@HiltViewModel
class InventoryViewModel @Inject constructor() : ViewModelStore<InventoryState>(InventoryState.EMPTY), InventoryAction {

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            actionSetLoading()
            val (gears, reagents) = InventoryResponseMapper(getInventory())

            updateState { state ->
                state.copy(
                    gears = gears,
                    reagents = reagents,
                    isLoading = false,
                )
            }
        }
    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        throwable.printStackTrace()
        oldState.copy(error = throwable, isLoading = false)
    }

    override fun actionSetLoading() = updateState { oldState ->
        oldState.copy(isLoading = true, error = null)
    }
}