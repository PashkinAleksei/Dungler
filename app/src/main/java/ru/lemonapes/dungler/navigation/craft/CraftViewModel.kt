package ru.lemonapes.dungler.navigation.craft

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.domain_models.CreateFood
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.mappers.CraftItemResponseMapper
import ru.lemonapes.dungler.navigation.craft.CraftViewState.CraftSwitchState
import ru.lemonapes.dungler.network.endpoints.getCraftItems
import ru.lemonapes.dungler.network.endpoints.postCreateItem
import ru.lemonapes.dungler.network.endpoints.postUpgradeItem
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.ViewModelAction
import ru.lemonapes.dungler.repositories.HeroStateRepository
import javax.inject.Inject

interface CraftAction : ViewModelAction {
    fun actionCreateItem(gearId: GearId)
    fun actionCreateFood(food: CreateFood)
    fun actionUpgradeItem(gearId: GearId)
    fun switchClick(state: CraftSwitchState)
}

@HiltViewModel
class CraftViewModel @Inject constructor(
    heroStateRepository: HeroStateRepository,
) : ParentViewModel<CraftViewState>(CraftViewState.getEmpty(), heroStateRepository), CraftAction {

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            actionSetLoading()
            val (createItems, createFood, upgradeItems, reagents, heroState) = CraftItemResponseMapper(getCraftItems())
            heroStateRepository.setNewHeroState(heroState)

            updateState { state ->
                state.copy(
                    createItems = createItems,
                    createFood = createFood,
                    upgradeItems = upgradeItems,
                    reagents = reagents,
                    isLoading = false,
                )
            }
        }
    }

    override fun actionCreateItem(gearId: GearId) = withActualState {
        launch(Dispatchers.IO + ceh) {
            val (createItems, createFood, upgradeItems, reagents, heroState) = CraftItemResponseMapper(
                postCreateItem(
                    gearId = gearId
                )
            )
            heroStateRepository.setNewHeroState(heroState)

            updateState { state ->
                state.copy(
                    createItems = createItems,
                    createFood = createFood,
                    upgradeItems = upgradeItems,
                    reagents = reagents.toPersistentMap()
                )
            }
        }
    }

    override fun actionCreateFood(food: CreateFood) = withActualState {
        /*launch(Dispatchers.IO + ceh) {
            val (createItems, createFood, upgradeItems, reagents, heroState) = CraftItemResponseMapper(postCreateFood(foodId = foodId))
            heroStateRepository.setNewHeroState(heroState)

            updateState { state ->
                state.copy(
                    createItems = createItems,
                    createFood = createFood,
                    upgradeItems = upgradeItems,
                    reagents = reagents.toPersistentMap()
                )
            }
        }*/
    }

    override fun actionUpgradeItem(gearId: GearId) = withActualState {
        launch(Dispatchers.IO + ceh) {
            val (createItems, createFood, upgradeItems, reagents, heroState) = CraftItemResponseMapper(
                postUpgradeItem(
                    gearId = gearId
                )
            )
            heroStateRepository.setNewHeroState(heroState)

            updateState { state ->
                state.copy(
                    createItems = createItems,
                    createFood = createFood,
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
        oldState.copy(error = throwable, isLoading = false)
    }

    override fun actionSetLoading() = updateState { oldState ->
        oldState.copy(isLoading = true, error = null)
    }
}