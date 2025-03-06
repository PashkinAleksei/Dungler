package ru.lemonapes.dungler.navigation.inventory

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.parent_view_model.State

data class InventoryState(
    override val error: Throwable? = null,
    override val isLoading: Boolean = true,

    val gears: ImmutableList<Gear> = persistentListOf(),
    val reagents: ImmutableMap<ReagentId, Int> = persistentMapOf(),
) : State {
    companion object {
        val EMPTY get() = InventoryState()
    }
}