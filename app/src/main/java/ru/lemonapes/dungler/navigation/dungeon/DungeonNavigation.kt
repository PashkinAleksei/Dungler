package ru.lemonapes.dungler.navigation.dungeon

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.dungeonNavigation() {
    composable<Screens.Dungeon> {
        val model: DungeonViewModel = hiltViewModel()
        val state = model.observeState().collectAsState().value
        val heroState = model.heroStateFlow.collectAsState().value
        ActionOnStart(model::actionStart)
        DungeonView(
            state = state,
            heroState = heroState,
            listener = DungeonListener(
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
    val stateListener: StateListener,
) {
    companion object {
        val EMPTY
            get() = DungeonListener(
                stateListener = StateListener.MOCK
            )
    }
}
