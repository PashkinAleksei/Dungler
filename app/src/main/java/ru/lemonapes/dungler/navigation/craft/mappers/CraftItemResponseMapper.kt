package ru.lemonapes.dungler.navigation.craft.mappers

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.DomainCreateItem
import ru.lemonapes.dungler.domain_models.DomainUpgradeItem
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.network.models.CraftItemsResponse

object CraftItemResponseMapper : (CraftItemsResponse) ->
Triple<List<DomainCreateItem>, List<DomainUpgradeItem>, ImmutableMap<ReagentId, Int>> {
    override fun invoke(response: CraftItemsResponse) =
        Triple(
            response.createItems.map(CraftItemMapper),
            response.upgradeItems.map(UpgradeItemMapper),
            response.reagents.toPersistentMap()
        )
}