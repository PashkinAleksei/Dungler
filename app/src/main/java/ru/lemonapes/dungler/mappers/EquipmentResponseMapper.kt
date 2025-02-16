package ru.lemonapes.dungler.mappers


import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.domain_models.StatId
import ru.lemonapes.dungler.network.models.EquipmentResponse

object EquipmentResponseMapper : (EquipmentResponse) -> Pair<ImmutableMap<GearType, Gear>, ImmutableMap<StatId, Int>> {
    override fun invoke(response: EquipmentResponse): Pair<ImmutableMap<GearType, Gear>, ImmutableMap<StatId, Int>> {
        val gears = response.equipment?.mapValues { (_, serverGear) ->
            GearMapper(serverGear)
        }

        return Pair(
            gears?.toImmutableMap() ?: persistentMapOf(),
            response.stats?.toImmutableMap() ?: persistentMapOf()
        )
    }
}