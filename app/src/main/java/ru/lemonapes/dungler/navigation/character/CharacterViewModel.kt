package ru.lemonapes.dungler.navigation.character

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.mappers.EquipmentResponseMapper
import ru.lemonapes.dungler.mappers.GearsToEquipResponseMapper
import ru.lemonapes.dungler.network.endpoints.deEquipItem
import ru.lemonapes.dungler.network.endpoints.equipItem
import ru.lemonapes.dungler.network.endpoints.getGearsToEquip
import ru.lemonapes.dungler.network.endpoints.loadEquipment
import ru.lemonapes.dungler.parent_store.ViewModelAction
import ru.lemonapes.dungler.parent_store.ViewModelStore
import ru.lemonapes.dungler.ui.item_comparison_dialog.DialogEquipmentState
import ru.lemonapes.dungler.ui.item_comparison_dialog.DialogEquipmentStateStatus

interface CharViewModelAction : ViewModelAction {
    fun actionGearClick(gearType: GearType, gear: Gear?)
    fun actionGearCompareClick(gear: Gear)
    fun actionEquip(gear: Gear)
    fun actionDeEquip(gearType: GearType)
    fun actionShowInventoryClick(gearType: GearType)
    fun actionBackToInventoryClick()
    fun actionGearDescriptionDialogDismiss()
}

class CharacterViewModel() :
    ViewModelStore<CharacterViewState>(CharacterViewState.EMPTY), CharViewModelAction {

    override val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        actionError(throwable)
    }

    private var inventoryLoadJob: Job? = null

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            actionSetLoading()
            val (gears, stats) = EquipmentResponseMapper(loadEquipment())

            updateState { state ->
                state.copy(
                    gears = gears,
                    stats = stats,
                    isLoading = false
                )
            }
        }
    }

    override fun actionGearClick(gearType: GearType, gear: Gear?) {
        if (gear != null) {
            updateState { state ->
                state.copy(
                    dialogEquipmentState = DialogEquipmentState(
                        equippedGear = gear,
                        status = DialogEquipmentStateStatus.EQUIPPED
                    )
                )
            }
        } else {
            actionShowInventoryClick(gearType)
        }
    }

    override fun actionGearCompareClick(gear: Gear) {
        updateState { state ->
            state.copy(
                dialogEquipmentState = state.dialogEquipmentState?.copy(
                    gearToCompare = gear,
                    status = DialogEquipmentStateStatus.COMPARISON
                )
            )
        }
    }

    override fun actionEquip(gear: Gear) = withActualState {
        launch {
            val (gears, stats) = EquipmentResponseMapper(equipItem(gear))
            updateState { state ->
                state.copy(
                    gears = gears,
                    stats = stats,
                    dialogEquipmentState = null
                )
            }
        }
    }

    override fun actionDeEquip(gearType: GearType) = withActualState {
        launch {
            val (gears, stats) = EquipmentResponseMapper(deEquipItem(gearType))
            updateState { state ->
                state.copy(
                    gears = gears,
                    stats = stats,
                    dialogEquipmentState = null
                )
            }
        }
    }

    override fun actionShowInventoryClick(gearType: GearType) = withActualState {
        inventoryLoadJob = launch {
            var loadingState: DialogEquipmentState
            updateState { state ->
                loadingState = DialogEquipmentState(
                    equippedGear = state.dialogEquipmentState?.equippedGear,
                    inventoryList = persistentListOf(),
                    status = DialogEquipmentStateStatus.INVENTORY,
                    isLoading = true,
                )
                state.copy(
                    dialogEquipmentState = loadingState
                )
            }
            val inventoryList = GearsToEquipResponseMapper(getGearsToEquip(gearType))
            if (isActive) {
                updateState { state ->
                    state.copy(
                        dialogEquipmentState = state.dialogEquipmentState?.copy(
                            inventoryList = inventoryList,
                            isLoading = false,
                        )
                    )
                }
            }
        }
    }

    override fun actionBackToInventoryClick() = withActualState {
        if (isActive) {
            updateState { state ->
                state.copy(
                    dialogEquipmentState = state.dialogEquipmentState?.copy(
                        status = DialogEquipmentStateStatus.INVENTORY,
                    )
                )
            }
        }
    }

    override fun actionGearDescriptionDialogDismiss() {
        inventoryLoadJob?.cancel()
        updateState { state ->
            state.copy(
                dialogEquipmentState = null
            )
        }
    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        throwable.printStackTrace()
        oldState.copy(error = throwable, isLoading = false)
    }

    override fun actionSetLoading() = updateState { oldState ->
        oldState.copy(isLoading = true, error = null)
    }
}