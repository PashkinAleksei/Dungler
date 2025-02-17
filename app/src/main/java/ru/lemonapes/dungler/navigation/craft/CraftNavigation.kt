package ru.lemonapes.dungler.navigation.craft

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.domain_models.CraftGear
import ru.lemonapes.dungler.domain_models.CreateGear
import ru.lemonapes.dungler.domain_models.UpgradeGear
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.navigation.craft.CraftViewState.CraftSwitchState
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionStartOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.craftNavigation(
    navController: NavController,
) {
    composable<Screens.Craft>() {
        val model: CraftViewModel = viewModel(factory = CraftModelFactory())
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionStartOnStart(model)
        CraftView(
            state = state,
            listener = CraftListener(
                switchClick = {
                    model.switchClick(it)
                },
                craftItem = { item ->
                    when (item) {
                        is CreateGear -> model.actionCreateItem(item.gearId)
                        is UpgradeGear -> model.actionUpgradeItem(item.gearId)
                    }
                },
                onRetryClick = {
                    model.actionStart()
                }
            )
        )
    }
}

class CraftListener(
    override val onRetryClick: () -> Unit,
    val switchClick: (state: CraftSwitchState) -> Unit,
    val craftItem: (item: CraftGear) -> Unit,
) : StateListener {
    companion object {
        val EMPTY get() = CraftListener({}, {}, {})
    }
}