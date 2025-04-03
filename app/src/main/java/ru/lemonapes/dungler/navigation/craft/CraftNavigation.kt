package ru.lemonapes.dungler.navigation.craft

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.domain_models.CraftGear
import ru.lemonapes.dungler.domain_models.CreateFood
import ru.lemonapes.dungler.domain_models.CreateGear
import ru.lemonapes.dungler.domain_models.UpgradeGear
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.navigation.craft.CraftViewState.CraftSwitchState
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.craftNavigation() {
    composable<Screens.Craft> {
        val model: CraftViewModel = hiltViewModel()
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionOnStart(model::actionStart)
        CraftView(
            state = state,
            listener = CraftListener(
                stateListener = StateListener(
                    onRetryClick = {
                        model.actionStart()
                    },
                ),
                switchClick = {
                    model.switchClick(it)
                },
                craftItem = { item ->
                    when (item) {
                        is CreateGear -> model.actionCreateItem(item.gearId)
                        is UpgradeGear -> model.actionUpgradeItem(item.gearId)
                    }
                },
                craftFood = { food ->
                    model.actionCreateFood(food)
                }
            )
        )
    }
}

class CraftListener(
    val stateListener: StateListener,
    val switchClick: (state: CraftSwitchState) -> Unit,
    val craftItem: (gear: CraftGear) -> Unit,
    val craftFood: (food: CreateFood) -> Unit,
) {
    companion object {
        val EMPTY get() = CraftListener(StateListener.MOCK, {}, {}, {})
    }
}