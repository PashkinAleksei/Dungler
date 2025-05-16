package ru.lemonapes.dungler.navigation.character.skills_equipment

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.skillsEquipmentNavigation() {
    composable<Screens.EquipmentSkills> {
        val model: SkillsEquipmentViewModel = hiltViewModel()
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionOnStart(model::actionStart)
        SkillsEquipmentView(
            state = state,
            listener = SkillsEquipmentListener(
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

class SkillsEquipmentListener(
    val onSkillClick: () -> Unit,
    val stateListener: StateListener,
) {
    companion object {
        val MOCK
            get() = SkillsEquipmentListener(
                onSkillClick = {},
                stateListener = StateListener.MOCK,
            )
    }
}