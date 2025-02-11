package ru.lemonapes.dungler.navigation.craft

import ru.lemonapes.dungler.navigation.domain_models.DomainCraftItem
import ru.lemonapes.dungler.parent_store.State
import ru.lemonapes.dungler.network.models.CraftItem

data class CraftViewState(
    val items: List<DomainCraftItem>,
    val reagents: Map<String, Int>,
    val error: Throwable? = null
) : State {
    companion object {
        fun getEmpty() = CraftViewState(emptyList(), emptyMap())
    }
}