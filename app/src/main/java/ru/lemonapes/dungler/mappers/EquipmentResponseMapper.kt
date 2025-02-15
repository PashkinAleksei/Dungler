package ru.lemonapes.dungler.mappers


import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.network.models.EquipmentResponse

object EquipmentResponseMapper {
    fun map(response: EquipmentResponse): Pair<ImmutableMap<GearType, Gear>, ImmutableMap<String, Int>> {
        val gears: Map<GearType, Gear> = response.equipment.mapValues { (_, serverGear) ->
            GearMapper(serverGear)
        }
        return Pair(gears.toImmutableMap(), response.stats.toImmutableMap())
    }
}