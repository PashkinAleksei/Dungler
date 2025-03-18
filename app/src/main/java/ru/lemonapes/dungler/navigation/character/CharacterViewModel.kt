package ru.lemonapes.dungler.navigation.character

import dagger.hilt.android.lifecycle.HiltViewModel
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
import ru.lemonapes.dungler.network.endpoints.getEquipment
import ru.lemonapes.dungler.network.endpoints.getGearsToEquip
import ru.lemonapes.dungler.network.endpoints.patchDeEquipItem
import ru.lemonapes.dungler.network.endpoints.patchEquipItem
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.ViewModelAction
import ru.lemonapes.dungler.repositories.HeroStateRepository
import ru.lemonapes.dungler.ui.item_comparison_dialog.DialogEquipmentState
import ru.lemonapes.dungler.ui.item_comparison_dialog.DialogEquipmentStateStatus
import javax.inject.Inject

interface CharViewModelAction : ViewModelAction {
    fun actionGearClick(gearType: GearType, gear: Gear?)

    //Dialog actions
    fun actionGearCompareClick(gear: Gear)
    fun actionEquip(gear: Gear)
    fun actionDeEquip(gearType: GearType)
    fun actionShowInventoryClick(gearType: GearType)
    fun actionShowInventoryReload()
    fun actionBackToInventoryClick()
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
            val (gears, stats, heroState) = EquipmentResponseMapper(getEquipment())
            heroStateRepository.setNewHeroState(heroState)

            updateState { state ->
                state.copy(
                    gears = gears,
                    stats = stats,
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
        dialogLoadJob?.cancel()
        dialogLoadJob = launch(Dispatchers.IO + dialogCeh) {
            val (gears, stats, heroState) = EquipmentResponseMapper(patchEquipItem(gear))
            heroStateRepository.setNewHeroState(heroState)
            if (isActive) {
                updateState { state ->
                    state.copy(
                        gears = gears,
                        stats = stats,
                        dialogEquipmentState = null
                    )
                }
            }
        }
    }

    override fun actionDeEquip(gearType: GearType) = withActualState {
        dialogLoadJob?.cancel()
        dialogLoadJob = launch(Dispatchers.IO + dialogCeh) {
            val (gears, stats, heroState) = EquipmentResponseMapper(patchDeEquipItem(gearType))
            heroStateRepository.setNewHeroState(heroState)
            if (isActive) {
                updateState { state ->
                    state.copy(
                        gears = gears,
                        stats = stats,
                        dialogEquipmentState = null
                    )
                }
            }
        }
    }

    override fun actionShowInventoryClick(gearType: GearType) = withActualState {
        dialogLoadJob?.cancel()
        dialogLoadJob = launch(Dispatchers.IO + inventoryCeh) {
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

    override fun actionShowInventoryReload() = withActualState { state ->
        state.dialogEquipmentState?.equippedGear?.gearId?.gearType?.let { gearType ->
            actionShowInventoryClick(gearType)
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
        state.copy(dialogEquipmentState = state.dialogEquipmentState?.copy(error = throwable, isLoading = false))
    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        oldState.copy(error = throwable, isLoading = false)
    }

    override fun actionSetLoading() = updateState { oldState ->
        oldState.copy(isLoading = true, error = null)
    }
}