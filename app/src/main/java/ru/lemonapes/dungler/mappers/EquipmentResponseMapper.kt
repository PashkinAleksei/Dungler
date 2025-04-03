package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import ru.lemonapes.dungler.network.models.EquipmentResponse

object EquipmentResponseMapper : (EquipmentResponse) -> EquipmentMappingResult {
    override fun invoke(response: EquipmentResponse): EquipmentMappingResult {
        val gears = response.equipment?.gears?.mapValues { (_, serverGear) ->
            GearMapper(serverGear)
        }

        val food = response.equipment?.food?.let(FoodMapper)

        return EquipmentMappingResult(
            gears = gears?.toImmutableMap() ?: persistentMapOf(),
            food = food,
            stats = response.stats?.toImmutableMap() ?: persistentMapOf(),
            heroState = HeroStateMapper(response.serverHeroState)
        )
    }
}