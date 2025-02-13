package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.network.models.InventoryResponse

object InventoryResponseMapper : (InventoryResponse) -> Pair<List<Gear>, Map<ReagentId, Int>> {
    override fun invoke(response: InventoryResponse) =
        Pair(
            response.gears,
            response.reagents
        )
}