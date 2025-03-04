package ru.lemonapes.dungler.navigation.character

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.characterNavigation(
    navController: NavController,
) {
    composable<Screens.Character>() {
        val model: CharacterViewModel = viewModel(factory = CharacterModelFactory())
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionOnStart(model::actionStart)
        CharacterView(
            state = state,
            listener = CharacterListener(
                onGearClick = { gearType, gear ->
                    model.actionGearClick(gearType, gear)
                },
                onRetryClick = {
                    model.actionStart()
                },
            ),
            equipmentChangingDialogListener = EquipmentChangingDialogListener(
                gearShowInventoryClick = { gearType ->
                    model.actionShowInventoryClick(gearType)
                },
                gearCompareClick = { gear ->
                    model.actionGearCompareClick(gear)
                },
                onGearDescriptionDialogDismiss = {
                    model.actionGearDescriptionDialogDismiss()
                },
                gearEquipClick = { gear ->
                    model.actionEquip(gear)
                },
                gearDeEquipClick = { gearType ->
                    model.actionDeEquip(gearType)
                },
                backToInventoryClick = {
                    model.actionBackToInventoryClick()
                },
                onRetryClick = {
                    model.actionShowInventoryReload()
                },
            ),
        )
    }
}

class CharacterListener(
    override val onRetryClick: () -> Unit,
    val onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) : StateListener {
    companion object {
        val EMPTY
            get() = CharacterListener(
                onRetryClick = {},
                onGearClick = { _, _ -> },
            )
    }
}

class EquipmentChangingDialogListener(
    override val onRetryClick: () -> Unit,
    val gearShowInventoryClick: (gearType: GearType) -> Unit,
    val onGearDescriptionDialogDismiss: () -> Unit,
    val gearCompareClick: (gear: Gear) -> Unit,
    val gearEquipClick: (gear: Gear) -> Unit,
    val gearDeEquipClick: (gearType: GearType) -> Unit,
    val backToInventoryClick: () -> Unit,
) : StateListener {
    companion object {
        val EMPTY
            get() = EquipmentChangingDialogListener(
                onRetryClick = {},
                gearShowInventoryClick = {},
                onGearDescriptionDialogDismiss = {},
                gearCompareClick = {},
                gearEquipClick = {},
                gearDeEquipClick = {},
                backToInventoryClick = {},
            )
    }
}