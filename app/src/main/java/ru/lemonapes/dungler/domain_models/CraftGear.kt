package ru.lemonapes.dungler.domain_models

import kotlinx.collections.immutable.ImmutableMap

sealed interface CraftGear {
    val gearId: GearId
    val image: Int
    val stats: ImmutableMap<StatId, Int>
    val reagents: ImmutableMap<ReagentId, Int>
}