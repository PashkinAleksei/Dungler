package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.CreateGear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.domain_models.UpgradeGear
import ru.lemonapes.dungler.network.models.CraftItemsResponse

object CraftItemResponseMapper : (CraftItemsResponse) ->
Triple<ImmutableList<CreateGear>, ImmutableList<UpgradeGear>, ImmutableMap<ReagentId, Int>> {
    override fun invoke(response: CraftItemsResponse) =
        Triple(
            response.createItems?.map(CraftItemMapper)?.toPersistentList() ?: persistentListOf(),
            response.upgradeItems?.map(UpgradeItemMapper)?.toPersistentList() ?: persistentListOf(),
            response.reagents?.toPersistentMap() ?: persistentMapOf()
        )
}