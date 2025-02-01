package ru.lemonapes.dungler.navigation.craft

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.navigation.Screens

fun NavGraphBuilder.craftNavigation(
    navController: NavController,
) {
    composable<Screens.Craft>() {
        val model: CraftViewModel = viewModel(factory = CraftModelFactory())
        val state = model.observeState().collectAsState().value
        CraftView(state)
    }
}