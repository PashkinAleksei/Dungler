package ru.lemonapes.dungler.navigation.character

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.parent_store.State

data class CharacterViewState(
    val gears: ImmutableMap<GearType, Gear> = persistentMapOf(),
    val stats: ImmutableMap<String, Int> = persistentMapOf(),
    val error: Throwable? = null,
) : State {
    companion object {
        val EMPTY get() = CharacterViewState()
        val MOCK
            get() = CharacterViewState(
                gears = persistentMapOf(GearType.CHEST to Gear.MOCK),
                stats = persistentMapOf(
                    "strength" to 17,
                    "stamina" to 12,
                    "agility" to 12,
                    "intelligence" to 12,
                    "armor" to 4,
                    "total_health" to 120,
                    "damage_min" to 5,
                    "damage_max" to 6,
                    "protection" to 0,
                    "dodge_chance" to 0,
                    "crit_chance" to 0
                )
            )
    }
}
