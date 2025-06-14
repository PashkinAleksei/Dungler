package ru.lemonapes.dungler.navigation.dungeon

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.navigation.SkillSlot
import ru.lemonapes.dungler.navigation.composableWithBars
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.dungeonNavigation() {
    composableWithBars<Screens.Dungeon> {
        val model: DungeonViewModel = hiltViewModel()
        val state = model.observeState().collectAsState().value
        val heroState = model.heroStateFlow.collectAsState().value
        ActionOnStart(model::actionStart)
        DungeonView(
            state = state,
            heroState = heroState,
            listener = DungeonListener(
                onSkillClick = { skillSlot ->
                    model.activateSkill(skillSlot)
                },
                stateListener = StateListener(
                    onRetryClick = {
                        model.actionStart()
                    },
                ),
            )
        )
    }
}

class DungeonListener(
    val onSkillClick: (SkillSlot) -> Unit,
    val stateListener: StateListener,
) {
    companion object {
        val EMPTY
            get() = DungeonListener(
                onSkillClick = {},
                stateListener = StateListener.MOCK,
            )
    }
}
