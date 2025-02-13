package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.CreateGear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.domain_models.UpgradeGear
import ru.lemonapes.dungler.network.models.CraftItemsResponse

object CraftItemResponseMapper : (CraftItemsResponse) ->
Triple<List<CreateGear>, List<UpgradeGear>, ImmutableMap<ReagentId, Int>> {
    override fun invoke(response: CraftItemsResponse) =
        Triple(
            response.createItems.map(CraftItemMapper),
            response.upgradeItems.map(UpgradeItemMapper),
            response.reagents.toPersistentMap()
        )
}