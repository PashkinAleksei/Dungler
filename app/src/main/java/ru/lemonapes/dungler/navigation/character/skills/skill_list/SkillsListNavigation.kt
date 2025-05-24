package ru.lemonapes.dungler.navigation.character.skills.skill_list

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.skillsListNavigation() {
    composable<Screens.SkillList> { backStackEntry ->
        val model: SkillsListViewModel = hiltViewModel()
        val skillSlot = backStackEntry.toRoute<Screens.SkillList>().skillSlot
        model.setSkillSlot(skillSlot)
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionOnStart(model::actionStart)
        SkillsListView(
            state = state,
            listener = SkillsListListener(
                onSkillClick = { model.onSkillSelected(skillId = it) },
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
    val stateListener: StateListener,
) {
    companion object {
        val MOCK
            get() = SkillsListListener(
                onSkillClick = {},
                stateListener = StateListener.MOCK,
            )
    }
}