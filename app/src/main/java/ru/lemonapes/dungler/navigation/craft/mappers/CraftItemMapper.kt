package ru.lemonapes.dungler.navigation.craft.mappers

import ru.lemonapes.dungler.navigation.domain_models.DomainCreateItem
import ru.lemonapes.dungler.navigation.game_items_data.DEFAULT_GEAR_DATA
import ru.lemonapes.dungler.navigation.game_items_data.GEAR_DATA_MAP
import ru.lemonapes.dungler.network.models.CreateItem
import ru.lemonapes.dungler.network.models.CrreateItemsResponse

object CraftItemMapper : (CreateItem) -> DomainCreateItem {
    override fun invoke(craftItem: CreateItem) =
        DomainCreateItem(
            gearId = craftItem.gearId,
            gearType = craftItem.gearType,
            gearData = GEAR_DATA_MAP[craftItem.gearId] ?: DEFAULT_GEAR_DATA,
            stats = craftItem.stats,
            reagents = craftItem.reagents,
        )
}

object CreateItemResponseMapper : (CrreateItemsResponse) -> Pair<List<DomainCreateItem>, HashMap<String, Int>> {
    override fun invoke(response: CrreateItemsResponse) =
        Pair(
            response.items.map(CraftItemMapper),
            response.reagents
        )
}