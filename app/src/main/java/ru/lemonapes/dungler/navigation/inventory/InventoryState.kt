package ru.lemonapes.dungler.navigation.inventory

import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.parent_store.State

data class InventoryState(
    val gears: List<Gear> = emptyList(),
    val reagents: Map<ReagentId, Int> = emptyMap(),
    val error: Throwable? = null,
) : State {
    companion object {
        val EMPTY get() = InventoryState()
    }
}