package ru.lemonapes.dungler.navigation.inventory

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionStartOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.inventoryNavigation(
    navController: NavController,
) {
    composable<Screens.Inventory>() {
        val model: InventoryViewModel = viewModel(factory = InventoryModelFactory())
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionStartOnStart(model)
        InventoryView(state = state, listener = InventoryListener { model.actionStart() })
    }
}

class InventoryListener(
    override val onRetryClick: () -> Unit,
) : StateListener {
    companion object {
        val EMPTY get() = InventoryListener {}
    }
}