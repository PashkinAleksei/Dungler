package ru.lemonapes.dungler.navigation.character

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.navigation.Screens

fun NavGraphBuilder.characterNavigation(
    navController: NavController,
) {
    composable<Screens.Character>() {
        val model: CharacterViewModel = viewModel(factory = CharacterModelFactory())
        val state = model.observeState().collectAsState().value
        CharacterView(
            state = state,
            listener = CharacterListener(
                onGearClick = { gearType, gear ->
                    model.actionGearClick(gearType, gear)
                },
                onGearDescriptionDialogDismiss = {
                    model.actionGearDescriptionDialogDismiss()
                }),
        )
    }
}

class CharacterListener(
    val onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
    val onGearDescriptionDialogDismiss: () -> Unit,
) {
    companion object {
        val EMPTY
            get() = CharacterListener(
                onGearClick = { _, _ -> },
                onGearDescriptionDialogDismiss = {})
    }
}