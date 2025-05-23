package ru.lemonapes.dungler.navigation.character.skills.skill_list

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.skillsListNavigation() {
    composable<Screens.SkillList> {
        val model: SkillsListViewModel = hiltViewModel()
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionOnStart(model::actionStart)
        SkillsListView(
            state = state,
            listener = SkillsListListener(
                onSkillClick = {},
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
    val onSkillClick: () -> Unit,
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