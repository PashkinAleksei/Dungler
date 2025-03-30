package ru.lemonapes.dungler.navigation.character

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.mappers.EquipmentResponseMapper
import ru.lemonapes.dungler.mappers.GearsToEquipResponseMapper
import ru.lemonapes.dungler.network.endpoints.equipmentFood
import ru.lemonapes.dungler.network.endpoints.getEquipment
import ru.lemonapes.dungler.network.endpoints.getGearsToEquip
import ru.lemonapes.dungler.network.endpoints.patchDeEquipItem
import ru.lemonapes.dungler.network.endpoints.patchEquipGear
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.ViewModelAction
import ru.lemonapes.dungler.repositories.HeroStateRepository
import ru.lemonapes.dungler.ui.item_comparison_dialog.DialogEquipmentState
import javax.inject.Inject

interface CharViewModelAction : ViewModelAction {
    fun actionGearClick(gearType: GearType, gear: Gear?)
    fun actionFoodClick(food: Food?)

    //Dialog actions
    fun actionGearCompareClick(gear: Gear)
    fun actionEquip(gear: Gear)
    fun actionFoodEquip(food: Food)
    fun actionDeEquip(gearType: GearType)
    fun actionShowInventoryClick(gearType: GearType)
    fun actionShowInventoryReload()
    fun actionDialogBackClick()
    fun actionGearDescriptionDialogDismiss()
    fun actionDialogError(throwable: Throwable)
    fun inventoryDialogError(throwable: Throwable)
}

@HiltViewModel
class CharacterViewModel @Inject constructor(
    heroStateRepository: HeroStateRepository,
) : ParentViewModel<CharacterViewState>(CharacterViewState.EMPTY, heroStateRepository), CharViewModelAction {

    private val dialogCeh = CoroutineExceptionHandler { _, throwable ->
        actionDialogError(throwable)
    }

    private val inventoryCeh = CoroutineExceptionHandler { _, throwable ->
        inventoryDialogError(throwable)
    }

    private var dialogLoadJob: Job? = null

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            actionSetLoading()
            val result = EquipmentResponseMapper(getEquipment())
            heroStateRepository.setNewHeroState(result.heroState)

            updateState { state ->
                state.copy(
                    gears = result.gears,
                    food = result.food,
                    stats = result.stats,
                    isLoading = false,
                    dialogEquipmentState = null
                )
            }
        }
    }

    override fun actionGearClick(gearType: GearType, gear: Gear?) {
        if (gear != null) {
            updateState { state ->
                state.copy(
                    dialogEquipmentState = DialogEquipmentState.GearShowEquipped(
                        equippedGear = gear,
                    )
                )
            }
        } else {
            actionShowInventoryClick(gearType)
        }
    }

    override fun actionFoodClick(food: Food?) {
        if (food != null) {
            updateState { state ->
                state.copy(
                    dialogEquipmentState = DialogEquipmentState.FoodShowEquipped(
                        equippedFood = food,
                    )
                )
            }
        }
    }

    override fun actionGearCompareClick(gear: Gear) {
        updateState { state ->
            val equippedGear = (state.dialogEquipmentState as DialogEquipmentState.GearInventory).equippedGear
            val inventoryList = state.dialogEquipmentState.inventoryList
            state.copy(
                dialogEquipmentState = DialogEquipmentState.GearComparison(
                    equippedGear = equippedGear,
                    gearToCompare = gear,
                    inventoryList = inventoryList
                )
            )
        }
    }

    override fun actionEquip(gear: Gear) = withActualState {
        dialogLoadJob?.cancel()
        dialogLoadJob = launch(Dispatchers.IO + dialogCeh) {
            val result = EquipmentResponseMapper(patchEquipGear(gear))
            heroStateRepository.setNewHeroState(result.heroState)
            if (isActive) {
                updateState { state ->
                    state.copy(
                        gears = result.gears,
                        food = result.food,
                        stats = result.stats,
                        dialogEquipmentState = null
                    )
                }
            }
        }
    }

    override fun actionFoodEquip(food: Food) = withActualState {
        dialogLoadJob?.cancel()
        dialogLoadJob = launch(Dispatchers.IO + dialogCeh) {
            val result = EquipmentResponseMapper(equipmentFood(food.id))
            heroStateRepository.setNewHeroState(result.heroState)
            if (isActive) {
                updateState { state ->
                    state.copy(
                        gears = result.gears,
                        food = result.food,
                        stats = result.stats,
                        dialogEquipmentState = null
                    )
                }
            }
        }
    }

    override fun actionDeEquip(gearType: GearType) = withActualState {
        dialogLoadJob?.cancel()
        dialogLoadJob = launch(Dispatchers.IO + dialogCeh) {
            val result = EquipmentResponseMapper(patchDeEquipItem(gearType))
            heroStateRepository.setNewHeroState(result.heroState)
            if (isActive) {
                updateState { state ->
                    state.copy(
                        gears = result.gears,
                        food = result.food,
                        stats = result.stats,
                        dialogEquipmentState = null
                    )
                }
            }
        }
    }

    override fun actionShowInventoryClick(gearType: GearType) = withActualState {
        dialogLoadJob?.cancel()
        dialogLoadJob = launch(Dispatchers.IO + inventoryCeh) {
            updateState { state ->
                val equippedGear = if (state.dialogEquipmentState is DialogEquipmentState.GearShowEquipped)
                    state.dialogEquipmentState.equippedGear else null

                state.copy(
                    dialogEquipmentState = DialogEquipmentState.GearInventory(
                        equippedGear = equippedGear,
                        inventoryList = persistentListOf(),
                        isLoading = true,
                    )
                )
            }
            val inventoryList = GearsToEquipResponseMapper(getGearsToEquip(gearType))
            updateState { state ->
                if (state.dialogEquipmentState is DialogEquipmentState.GearInventory)
                    state.copy(
                        dialogEquipmentState = state.dialogEquipmentState.copy(
                            inventoryList = inventoryList,
                            isLoading = false
                        )
                    ) else state
            }
        }
    }

    override fun actionShowInventoryReload() = withActualState { state ->
        state.dialogEquipmentState?.let { dialogEquipmentData ->
            if (dialogEquipmentData is DialogEquipmentState.GearShowEquipped) {
                actionShowInventoryClick(dialogEquipmentData.equippedGear.gearId.gearType)
            }
        }
    }

    override fun actionDialogBackClick() = withActualState {
        updateState { state ->
            val dialogState = state.dialogEquipmentState
            when {
                dialogState is DialogEquipmentState.GearComparison -> {
                    state.copy(
                        dialogEquipmentState = DialogEquipmentState.GearInventory(
                            equippedGear = dialogState.equippedGear,
                            inventoryList = dialogState.inventoryList,
                        )
                    )
                }

                dialogState is DialogEquipmentState.GearInventory && dialogState.equippedGear != null -> {
                    state.copy(
                        dialogEquipmentState = DialogEquipmentState.GearShowEquipped(
                            equippedGear = dialogState.equippedGear,
                        )
                    )
                }

                else -> state
            }
        }
    }

    override fun actionGearDescriptionDialogDismiss() {
        dialogLoadJob?.cancel()
        updateState { state ->
            state.copy(
                dialogEquipmentState = null
            )
        }
    }

    override fun actionDialogError(throwable: Throwable) {

    }

    override fun inventoryDialogError(throwable: Throwable) = updateState { state ->
        if (state.dialogEquipmentState is DialogEquipmentState.GearInventory) {
            state.copy(
                dialogEquipmentState = state.dialogEquipmentState.copy(
                    error = throwable,
                    isLoading = false
                )
            )
        } else {
            state
        }
    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        oldState.copy(error = throwable, isLoading = false)
    }

    override fun actionSetLoading() = updateState { oldState ->
        oldState.copy(isLoading = true, error = null)
    }
}