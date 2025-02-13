package ru.lemonapes.dungler.navigation.craft

import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.Utils.Companion.log
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.mappers.CraftItemResponseMapper
import ru.lemonapes.dungler.navigation.craft.CraftViewState.CraftSwitchState
import ru.lemonapes.dungler.network.endpoints.createItem
import ru.lemonapes.dungler.network.endpoints.loadCraftItems
import ru.lemonapes.dungler.network.endpoints.upgradeItem
import ru.lemonapes.dungler.parent_store.ViewModelStore

interface CraftAction {
    fun actionStart()
    fun actionCreateItem(gearId:GearId)
    fun actionUpgradeItem(gearId:GearId)
    fun switchClick(state: CraftSwitchState)
    fun actionError(throwable: Throwable)
    fun actionClearError()
}

class CraftViewModel :
    ViewModelStore<CraftViewState>(CraftViewState.getEmpty()), CraftAction {

    override val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        actionError(throwable)
    }

    init {
        actionStart()
    }

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            val (createItems, upgradeItems, reagents) = CraftItemResponseMapper(loadCraftItems())

            updateState { state ->
                state.copy(
                    createItems = createItems,
                    upgradeItems = upgradeItems,
                    reagents = reagents
                )
            }
        }
    }

    override fun actionCreateItem(gearId:GearId) = withActualState {
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

    override fun actionUpgradeItem(gearId:GearId) = withActualState {
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
        log(throwable)
        oldState.copy(error = throwable)
    }

    override fun actionClearError() = updateState { oldState ->
        oldState.copy(error = null)
    }
}