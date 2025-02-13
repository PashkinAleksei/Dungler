package ru.lemonapes.dungler.domain_models

import kotlinx.collections.immutable.ImmutableMap

sealed interface DomainCraftItem {
    val gearId: GearId
    val image: Int
    val gearType: GearType
    val stats: ImmutableMap<String, Int>
    val reagents: ImmutableMap<ReagentId, Int>
}