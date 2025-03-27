package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableMap
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.domain_models.StatId
import ru.lemonapes.dungler.hero_state.HeroState

data class EquipmentMappingResult(
    val gears: ImmutableMap<GearType, Gear>,
    val food: Food?,
    val stats: ImmutableMap<StatId, Int>,
    val heroState: HeroState,
) 