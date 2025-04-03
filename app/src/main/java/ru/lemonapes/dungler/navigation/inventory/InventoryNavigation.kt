package ru.lemonapes.dungler.navigation.inventory

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.inventoryNavigation() {
    composable<Screens.Inventory> {
        val model: InventoryViewModel = hiltViewModel()
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionOnStart(model::actionStart)
        InventoryView(
            state = state, listener = InventoryListener(
                stateListener = StateListener(
                    onRetryClick = {
                        model.actionStart()
                    },
                ),
            )
        )
    }
}

class InventoryListener(
    val stateListener: StateListener,
) {
    companion object {
        val EMPTY get() = InventoryListener(StateListener.MOCK)
    }
}