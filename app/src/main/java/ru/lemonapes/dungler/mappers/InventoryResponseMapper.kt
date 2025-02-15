package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.network.models.InventoryResponse

object InventoryResponseMapper : (InventoryResponse) -> Pair<List<Gear>, ImmutableMap<ReagentId, Int>> {
    override fun invoke(response: InventoryResponse) =
        Pair(
            response.gears.map(GearMapper),
            response.reagents.toPersistentMap()
        )
}