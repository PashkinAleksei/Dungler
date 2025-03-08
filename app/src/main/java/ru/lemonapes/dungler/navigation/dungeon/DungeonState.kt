package ru.lemonapes.dungler.navigation.dungeon

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.lemonapes.dungler.domain_models.Enemy
import ru.lemonapes.dungler.parent_view_model.State

data class DungeonState(
    override val isLoading: Boolean = true,
    override val error: Throwable? = null,
    val enemies: ImmutableList<Enemy> = persistentListOf(),
) : State {
    companion object {
        val MOCK get() = DungeonState(isLoading = false, enemies = persistentListOf(Enemy.MOCK))
        val EMPTY get() = DungeonState()
    }
}