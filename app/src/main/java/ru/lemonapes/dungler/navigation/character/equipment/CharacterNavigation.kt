package ru.lemonapes.dungler.navigation.character.equipment

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.characterNavigation() {
    composable<Screens.Equipment> {
        val model: CharacterViewModel = hiltViewModel()
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionOnStart(model::actionStart)
        CharacterView(
            state = state,
            listener = CharacterListener(
                onGearClick = { gearType, gear ->
                    model.actionGearClick(gearType, gear)
                },
                onFoodClick = {
                    model.actionFoodClick()
                },
                stateListener = StateListener(
                    onRetryClick = {
                        model.actionStart()
                    },
                ),
                equipmentChangingDialogListener = EquipmentChangingDialogListener(
                    gearListener = GearChangingDialogListener(
                        gearShowInventoryClick = { gearType ->
                            model.actionShowGearInventoryClick(gearType)
                        },
                        gearCompareClick = { gear ->
                            model.actionGearCompareClick(gear)
                        },
                        gearEquipClick = { gear ->
                            model.actionEquip(gear)
                        },
                        gearDeEquipClick = { gearType ->
                            model.actionGearDeEquip(gearType)
                        },
                    ),
                    foodListener = FoodChangingDialogListener(
                        foodCompareClick = { food ->
                            model.actionFoodCompareClick(food)
                        },
                        foodEquipClick = { food ->
                            model.actionFoodEquip(food)
                        },
                        foodShowInventoryClick = {
                            model.actionShowFoodInventoryClick()
                        },
                        foodDeEquipClick = {
                            model.actionFoodDeEquip()
                        },
                    ),
                    stateListener = StateListener(
                        onRetryClick = {
                            model.actionShowInventoryReload()
                        },
                    ),
                    backToInventoryClick = {
                        model.actionDialogBackClick()
                    },
                    onDismiss = {
                        model.actionGearDescriptionDialogDismiss()
                    },
                ),
            ),
        )
    }
}

class CharacterListener(
    val onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
    val onFoodClick: () -> Unit,
    val stateListener: StateListener,
    val equipmentChangingDialogListener: EquipmentChangingDialogListener,
) {
    companion object {
        val MOCK
            get() = CharacterListener(
                onGearClick = { _, _ -> },
                onFoodClick = {},
                stateListener = StateListener.MOCK,
                equipmentChangingDialogListener = EquipmentChangingDialogListener.MOCK,
            )
    }
}

class EquipmentChangingDialogListener(
    val gearListener: GearChangingDialogListener,
    val foodListener: FoodChangingDialogListener,
    val stateListener: StateListener,
    val onDismiss: () -> Unit,
    val backToInventoryClick: () -> Unit,
) {
    companion object {
        val MOCK
            get() = EquipmentChangingDialogListener(
                gearListener = GearChangingDialogListener.MOCK,
                foodListener = FoodChangingDialogListener.MOCK,
                stateListener = StateListener.MOCK,
                onDismiss = {},
                backToInventoryClick = {},
            )
    }
}

open class GearChangingDialogListener(
    val gearShowInventoryClick: (gearType: GearType) -> Unit,
    val gearCompareClick: (gear: Gear) -> Unit,
    val gearEquipClick: (gear: Gear) -> Unit,
    val gearDeEquipClick: (gearType: GearType) -> Unit,
) {
    companion object {
        val MOCK
            get() = GearChangingDialogListener(
                gearShowInventoryClick = {},
                gearCompareClick = {},
                gearEquipClick = {},
                gearDeEquipClick = {},
            )
    }
}

open class FoodChangingDialogListener(
    val foodCompareClick: (food: Food) -> Unit,
    val foodEquipClick: (food: Food) -> Unit,
    val foodShowInventoryClick: () -> Unit,
    val foodDeEquipClick: () -> Unit,
) {
    companion object {
        val MOCK
            get() = FoodChangingDialogListener(
                foodCompareClick = {},
                foodEquipClick = {},
                foodShowInventoryClick = {},
                foodDeEquipClick = {},
            )
    }
}