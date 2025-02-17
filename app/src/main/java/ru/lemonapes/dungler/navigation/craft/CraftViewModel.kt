package ru.lemonapes.dungler.navigation.craft

import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.mappers.CraftItemResponseMapper
import ru.lemonapes.dungler.navigation.craft.CraftViewState.CraftSwitchState
import ru.lemonapes.dungler.network.endpoints.createItem
import ru.lemonapes.dungler.network.endpoints.loadCraftItems
import ru.lemonapes.dungler.network.endpoints.upgradeItem
import ru.lemonapes.dungler.parent_store.ViewModelAction
import ru.lemonapes.dungler.parent_store.ViewModelStore

interface CraftAction : ViewModelAction {
    fun actionCreateItem(gearId: GearId)
    fun actionUpgradeItem(gearId: GearId)
    fun switchClick(state: CraftSwitchState)
}

class CraftViewModel :
    ViewModelStore<CraftViewState>(CraftViewState.getEmpty()), CraftAction {

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            actionSetLoading()
            val (createItems, upgradeItems, reagents) = CraftItemResponseMapper(loadCraftItems())

            updateState { state ->
                state.copy(
                    createItems = createItems,
                    upgradeItems = upgradeItems,
                    reagents = reagents,
                    isLoading = false,
                )
            }
        }
    }

    override fun actionCreateItem(gearId: GearId) = withActualState {
        launch(Dispatchers.IO + ceh) {
            val (createItems, upgradeItems, reagents) = CraftItemResponseMapper(createItem(gearId = gearId))

            updateState { state ->
                state.copy(
                    createItems = createItems,
                    upgradeItems = upgradeItems,
                    reagents = reagents.toPersistentMap()
                )
            }
        }
    }

    override fun actionUpgradeItem(gearId: GearId) = withActualState {
        launch(Dispatchers.IO + ceh) {
            val (createItems, upgradeItems, reagents) = CraftItemResponseMapper(upgradeItem(gearId = gearId))

            updateState { state ->
                state.copy(
                    createItems = createItems,
                    upgradeItems = upgradeItems,
                    reagents = reagents.toPersistentMap()
                )
            }
        }
    }

    override fun switchClick(state: CraftSwitchState) {
        updateState { oldState ->
            oldState.copy(switchState = state)
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