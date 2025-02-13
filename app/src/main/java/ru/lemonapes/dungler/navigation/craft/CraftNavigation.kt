package ru.lemonapes.dungler.navigation.craft

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.domain_models.DomainCraftItem
import ru.lemonapes.dungler.domain_models.DomainCreateItem
import ru.lemonapes.dungler.domain_models.DomainUpgradeItem
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.navigation.craft.CraftViewState.CraftSwitchState

fun NavGraphBuilder.craftNavigation(
    navController: NavController,
) {
    composable<Screens.Craft>() {
        val model: CraftViewModel = viewModel(factory = CraftModelFactory())
        val state = model.observeState().collectAsState().value
        CraftView(
            state,
            CraftListener(
                switchClick = {
                    model.switchClick(it)
                },
                craftItem = {item->
                    when(item){
                        is DomainCreateItem -> model.actionCreateItem(item.gearId)
                        is DomainUpgradeItem -> model.actionUpgradeItem(item.gearId)
                    }
                },
            )
        )
    }
}

class CraftListener(
    val switchClick: (state: CraftSwitchState) -> Unit,
    val craftItem: (item: DomainCraftItem) -> Unit,
) {
    companion object {
        val EMPTY get() = CraftListener({}, {})
    }
}