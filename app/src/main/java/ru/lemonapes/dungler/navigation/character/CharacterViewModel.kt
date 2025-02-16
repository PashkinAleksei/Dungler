package ru.lemonapes.dungler.navigation.character

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.mappers.EquipmentResponseMapper
import ru.lemonapes.dungler.mappers.GearsToEquipResponseMapper
import ru.lemonapes.dungler.network.endpoints.getGearsToEquip
import ru.lemonapes.dungler.network.endpoints.loadEquipment
import ru.lemonapes.dungler.parent_store.ViewModelStore
import ru.lemonapes.dungler.ui.item_comparing_dialog.GearDescriptionDialogState
import ru.lemonapes.dungler.ui.item_comparing_dialog.GearDescriptionDialogStatus

interface CharAction {
    fun actionStart()
    fun actionGearClick(gearType: GearType, gear: Gear?)
    fun actionGearChooseClick(gearType: GearType)
    fun actionGearDescriptionDialogDismiss()
    fun actionError(throwable: Throwable)
    fun actionClearError()
}

class CharacterViewModel() :
    ViewModelStore<CharacterViewState>(CharacterViewState.EMPTY), CharAction {

    override val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        actionError(throwable)
    }

    init {
        actionStart()
    }

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            val (gears, stats) = EquipmentResponseMapper(loadEquipment())

            updateState { state ->
                state.copy(gears = gears, stats = stats)
            }
        }
    }

    override fun actionGearClick(gearType: GearType, gear: Gear?) {
        if (gear != null) {
            updateState { state ->
                state.copy(
                    gearDescriptionDialogState = GearDescriptionDialogState(
                        gear = gear,
                        status = GearDescriptionDialogStatus.EQUIPPED
                    )
                )
            }
        }
    }

    override fun actionGearChooseClick(gearType: GearType) = withActualState {
        launch {
            updateState { state ->
                state.copy(
                    gearDescriptionDialogState = state.gearDescriptionDialogState.copy(
                        inventoryList = persistentListOf(),
                        status = GearDescriptionDialogStatus.INVENTORY,
                        isLoading = true,
                    )
                )
            }
            val inventoryList = GearsToEquipResponseMapper(getGearsToEquip(gearType))
            updateState { state ->
                state.copy(
                    gearDescriptionDialogState = state.gearDescriptionDialogState.copy(
                        inventoryList = inventoryList,
                        status = GearDescriptionDialogStatus.INVENTORY,
                        isLoading = false,
                    )
                )
            }
        }
    }

    override fun actionGearDescriptionDialogDismiss() {
        updateState { state ->
            state.copy(
                gearDescriptionDialogState = GearDescriptionDialogState(
                    gear = null,
                    status = GearDescriptionDialogStatus.CLOSED
                )
            )
        }
    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        throwable.printStackTrace()
        oldState.copy(error = throwable)
    }

    override fun actionClearError() = updateState { oldState ->
        oldState.copy(error = null)
    }
}