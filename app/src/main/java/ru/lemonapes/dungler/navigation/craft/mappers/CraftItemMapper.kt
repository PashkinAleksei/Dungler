package ru.lemonapes.dungler.navigation.craft.mappers

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.DomainCreateItem
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.navigation.game_items_data.DEFAULT_GEAR_DATA
import ru.lemonapes.dungler.navigation.game_items_data.GEAR_DATA_MAP
import ru.lemonapes.dungler.network.models.CreateItem
import ru.lemonapes.dungler.network.models.CreateItemsResponse

object CraftItemMapper : (CreateItem) -> DomainCreateItem {
    override fun invoke(craftItem: CreateItem): DomainCreateItem {
        val gearData = GEAR_DATA_MAP[craftItem.gearId]
        val image = gearData?.imageList?.firstOrNull()
            ?: DEFAULT_GEAR_DATA.imageList.first()

        return DomainCreateItem(
            gearId = craftItem.gearId,
            gearType = craftItem.gearType,
            image = image,
            stats = craftItem.stats.toPersistentMap(),
            reagents = craftItem.reagents.toPersistentMap(),
        )
    }
}

object CreateItemResponseMapper : (CreateItemsResponse) -> Pair<List<DomainCreateItem>, ImmutableMap<ReagentId, Int>> {
    override fun invoke(response: CreateItemsResponse) =
        Pair(
            response.items.map(CraftItemMapper),
            response.reagents.toPersistentMap()
        )
}