package ru.lemonapes.dungler.navigation.inventory

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.parent_store.State

data class InventoryState(
    val gears: List<Gear> = emptyList(),
    val reagents: ImmutableMap<ReagentId, Int> = persistentMapOf(),
    val error: Throwable? = null,
) : State {
    companion object {
        val EMPTY get() = InventoryState()
    }
}