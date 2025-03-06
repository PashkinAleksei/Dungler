package ru.lemonapes.dungler.navigation.dungeon_list

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.dungeonListNavigation() {
    composable<Screens.DungeonList>() {
        val model: DungeonListViewModel = hiltViewModel()
        val state = model.observeState().collectAsState().value
        ActionOnStart(model::actionStart)

        DungeonListView(
            state = state,
            listener = DungeonListListener(
                onRetryClick = {
                    model.actionStart()
                },
                onEnterDungeonsClick = { dungeonId ->
                    model.actionEnterDungeons(dungeonId)
                }
            )
        )
    }
}

class DungeonListListener(
    override val onRetryClick: () -> Unit,
    val onEnterDungeonsClick: (dungeonId: String) -> Unit,
) : StateListener {
    companion object {
        val EMPTY
            get() = DungeonListListener(
                onRetryClick = {},
                onEnterDungeonsClick = {}
            )
    }
}