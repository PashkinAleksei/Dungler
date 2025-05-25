package ru.lemonapes.dungler.navigation.character.skills.skill_list

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.navigation.composableWithBars
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener
import ru.lemonapes.dungler.ui.observeUiEvents

fun NavGraphBuilder.skillsListNavigation(navController: NavController) {
    composableWithBars<Screens.SkillList>(
        heroTopBarVisible = false,
        bottomBarVisible = false,
    ) { backStackEntry ->
        val model: SkillsListViewModel = hiltViewModel()
        model.observeUiEvents(navController)

        val skillSlot = backStackEntry.toRoute<Screens.SkillList>().skillSlot
        model.setSkillSlot(skillSlot)
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionOnStart(model::actionStart)
        SkillsListView(
            state = state,
            listener = SkillsListListener(
                onSkillClick = { model.onSkillSelected(skillId = it) },
                onClose = { navController.popBackStack() },
                stateListener = StateListener(
                    onRetryClick = {
                        model.actionStart()
                    },
                ),
            )
        )
    }
}

class SkillsListListener(
    val onSkillClick: (SkillId) -> Unit,
    val onClose: () -> Unit,
    val stateListener: StateListener,
) {
    companion object {
        val MOCK
            get() = SkillsListListener(
                onSkillClick = {},
                onClose = {},
                stateListener = StateListener.MOCK,
            )
    }
}