package ru.lemonapes.dungler.navigation.character

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.navigation.Screens

fun NavGraphBuilder.characterNavigation(
    navController: NavController,
) {
    composable<Screens.Character>() {
        val model: CharacterViewModel = viewModel(factory = CharacterModelFactory())
        val state = model.observeState().collectAsState().value
        CharacterView(state)
    }
}