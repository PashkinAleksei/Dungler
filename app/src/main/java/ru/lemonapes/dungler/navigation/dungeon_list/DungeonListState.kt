package ru.lemonapes.dungler.navigation.dungeon_list

import ru.lemonapes.dungler.parent_view_model.State

data class DungeonListState(
    override val isLoading: Boolean = true,
    override val error: Throwable? = null,
) : State {
    companion object {
        val MOCK get() = DungeonListState(isLoading = false)
        val EMPTY get() = DungeonListState()
    }
}