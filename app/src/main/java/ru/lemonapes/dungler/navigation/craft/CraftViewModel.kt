package ru.lemonapes.dungler.navigation.craft

import ru.lemonapes.dungler.parent_store.ViewModelStore
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.navigation.craft.CraftViewState.CraftSwitchState
import ru.lemonapes.dungler.navigation.craft.mappers.CreateItemResponseMapper
import ru.lemonapes.dungler.navigation.craft.mappers.UpgradeItemResponseMapper
import ru.lemonapes.dungler.network.endpoints.loadCreateItems
import ru.lemonapes.dungler.network.endpoints.loadUpgradeItems

interface CraftAction {
    fun actionStart()
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
            val getCreateItemsJob = async {
                loadCreateItems()
            }
            val getUpgradeItemsJob = async {
                loadUpgradeItems()
            }
            val (createItems, reagents) = CreateItemResponseMapper(getCreateItemsJob.await())
            val (upgradeItems, _) = UpgradeItemResponseMapper(getUpgradeItemsJob.await())

            updateState { state ->
                state.copy(
                    createItems = createItems,
                    upgradeItems = upgradeItems,
                    reagents = reagents
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
        oldState.copy(error = throwable)
    }

    override fun actionClearError() = updateState { oldState ->
        oldState.copy(error = null)
    }
}