package ru.lemonapes.dungler.navigation.character.equipment

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
import ru.lemonapes.dungler.mappers.FoodToEquipResponseMapper
import ru.lemonapes.dungler.mappers.GearsToEquipResponseMapper
import ru.lemonapes.dungler.navigation.character.item_comparison_dialog.DialogEquipmentState
import ru.lemonapes.dungler.network.endpoints.getEquipment
import ru.lemonapes.dungler.network.endpoints.getFoodToEquip
import ru.lemonapes.dungler.network.endpoints.getGearsToEquip
import ru.lemonapes.dungler.network.endpoints.patchDeEquipFood
import ru.lemonapes.dungler.network.endpoints.patchDeEquipGear
import ru.lemonapes.dungler.network.endpoints.patchEquipFood
import ru.lemonapes.dungler.network.endpoints.patchEquipGear
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.ViewModelAction
import ru.lemonapes.dungler.repositories.HeroStateRepository
import javax.inject.Inject

interface CharViewModelAction : ViewModelAction {
    fun actionGearClick(gearType: GearType, gear: Gear?)
    fun actionFoodClick()

    //Dialog actions
    fun actionGearCompareClick(gear: Gear)
    fun actionFoodCompareClick(food: Food)
    fun actionEquip(gear: Gear)
    fun actionFoodEquip(food: Food)
    fun actionGearDeEquip(gearType: GearType)
    fun actionFoodDeEquip()
    fun actionShowGearInventoryClick(gearType: GearType)
    fun actionShowFoodInventoryClick()
    fun actionShowInventoryReload()
    fun actionGearDescriptionDialogDismiss()

    fun actionDialogError(throwable: Throwable)
    fun actionDialogBackClick()
    fun inventoryDialogError(throwable: Throwable)
}

@HiltViewModel
class CharacterViewModel @Inject constructor(
    heroStateRepository: HeroStateRepository,
) : ParentViewModel<CharacterViewState>(CharacterViewState.EMPTY, heroStateRepository), CharViewModelAction {

    private val dialogCeh = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        throwable.handleResponseError()
        actionDialogError(throwable)
    }

    private val inventoryCeh = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        throwable.handleResponseError()
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
            actionShowGearInventoryClick(gearType)
        }
    }

    override fun actionFoodClick() {
        if (observeState().value.food != null) {
            updateState { state ->
                state.food?.let { food ->
                    state.copy(
                        dialogEquipmentState = DialogEquipmentState.FoodShowEquipped(
                            equippedFood = food,
                        )
                    )
                } ?: state
            }
        } else {
            actionShowFoodInventoryClick()
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

    override fun actionFoodCompareClick(food: Food) {
        updateState { state ->
            val equippedFood = (state.dialogEquipmentState as DialogEquipmentState.FoodInventory).equippedFood
            val inventoryList = state.dialogEquipmentState.inventoryList
            state.copy(
                dialogEquipmentState = DialogEquipmentState.FoodComparison(
                    equippedFood = equippedFood,
                    foodToCompare = food,
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
            val result = EquipmentResponseMapper(patchEquipFood(food.id))
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

    override fun actionGearDeEquip(gearType: GearType) = withActualState {
        dialogLoadJob?.cancel()
        dialogLoadJob = launch(Dispatchers.IO + dialogCeh) {
            val result = EquipmentResponseMapper(patchDeEquipGear(gearType))
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

    override fun actionFoodDeEquip() = withActualState {
        dialogLoadJob?.cancel()
        dialogLoadJob = launch(Dispatchers.IO + dialogCeh) {
            val result = EquipmentResponseMapper(patchDeEquipFood())
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

    override fun actionShowGearInventoryClick(gearType: GearType) = withActualState {
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

    override fun actionShowFoodInventoryClick() = withActualState {
        dialogLoadJob?.cancel()
        dialogLoadJob = launch(Dispatchers.IO + inventoryCeh) {
            updateState { state ->
                val equippedFood = if (state.dialogEquipmentState is DialogEquipmentState.FoodShowEquipped)
                    state.dialogEquipmentState.equippedFood else null

                state.copy(
                    dialogEquipmentState = DialogEquipmentState.FoodInventory(
                        equippedFood = equippedFood,
                        inventoryList = persistentListOf(),
                        isLoading = true,
                    )
                )
            }
            val inventoryList = FoodToEquipResponseMapper(getFoodToEquip())
            updateState { state ->
                if (state.dialogEquipmentState is DialogEquipmentState.FoodInventory)
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
            when (dialogEquipmentData) {
                is DialogEquipmentState.GearShowEquipped -> {
                    actionShowGearInventoryClick(dialogEquipmentData.equippedGear.gearId.gearType)
                }

                is DialogEquipmentState.FoodShowEquipped -> {
                    actionShowFoodInventoryClick()
                }

                else -> Unit
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

                dialogState is DialogEquipmentState.FoodComparison -> {
                    state.copy(
                        dialogEquipmentState = DialogEquipmentState.FoodInventory(
                            equippedFood = dialogState.equippedFood,
                            inventoryList = dialogState.inventoryList,
                        )
                    )
                }

                dialogState is DialogEquipmentState.FoodInventory && dialogState.equippedFood != null -> {
                    state.copy(
                        dialogEquipmentState = DialogEquipmentState.FoodShowEquipped(
                            equippedFood = dialogState.equippedFood,
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