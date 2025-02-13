package ru.lemonapes.dungler.navigation.inventory

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.navigation.Screens

fun NavGraphBuilder.inventoryNavigation(
    navController: NavController,
) {
    composable<Screens.Inventory>() {
        val model: InventoryViewModel = viewModel(factory = InventoryModelFactory())
        val state = model.observeState().collectAsState().value
        InventoryView(state, InventoryListener { })
    }
}

class InventoryListener(val click: (Boolean) -> Unit) {
    companion object {
        val EMPTY get() = InventoryListener {}
    }
}