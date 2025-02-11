package ru.lemonapes.dungler.navigation.craft.mappers

import ru.lemonapes.dungler.navigation.domain_models.DomainCraftItem
import ru.lemonapes.dungler.navigation.game_items_data.DEFAULT_GEAR_DATA
import ru.lemonapes.dungler.navigation.game_items_data.GEAR_DATA_MAP
import ru.lemonapes.dungler.network.models.CraftItem
import ru.lemonapes.dungler.network.models.CraftItemsResponse

object CraftItemMapper : (CraftItem) -> DomainCraftItem {
    override fun invoke(craftItem: CraftItem) =
        DomainCraftItem(
            gearId = craftItem.gearId,
            gearType = craftItem.gearType,
            gearData = GEAR_DATA_MAP[craftItem.gearId] ?: DEFAULT_GEAR_DATA,
            stats = craftItem.stats,
            reagents = craftItem.reagents,
        )
}

object CraftItemResponseMapper : (CraftItemsResponse) -> Pair<List<DomainCraftItem>, HashMap<String, Int>> {
    override fun invoke(response: CraftItemsResponse) =
        Pair(
            response.items.map(CraftItemMapper),
            response.reagents
        )
}