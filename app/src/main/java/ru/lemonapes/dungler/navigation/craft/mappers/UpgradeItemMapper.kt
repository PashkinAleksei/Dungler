package ru.lemonapes.dungler.navigation.craft.mappers

import ru.lemonapes.dungler.navigation.domain_models.DomainUpgradeItem
import ru.lemonapes.dungler.navigation.game_items_data.DEFAULT_GEAR_DATA
import ru.lemonapes.dungler.navigation.game_items_data.GEAR_DATA_MAP
import ru.lemonapes.dungler.network.models.UpgradeItem
import ru.lemonapes.dungler.network.models.UpgradeItemsResponse

object UpgradeItemMapper : (UpgradeItem) -> DomainUpgradeItem {
    override fun invoke(item: UpgradeItem): DomainUpgradeItem {
        val gearData = GEAR_DATA_MAP[item.gearId]
        val image =
            gearData?.imageList
                ?.getOrNull(item.level)
                ?: gearData?.imageList?.lastOrNull()
                ?: DEFAULT_GEAR_DATA.imageList.last()

        return DomainUpgradeItem(
            gearId = item.gearId,
            gearType = item.gearType,
            image = image,
            level = item.level,
            stats = item.stats,
            nextStats = item.nextStats,
            reagents = item.reagents
        )
    }
}

object UpgradeItemResponseMapper : (UpgradeItemsResponse) -> Pair<List<DomainUpgradeItem>, HashMap<String, Int>> {
    override fun invoke(response: UpgradeItemsResponse): Pair<List<DomainUpgradeItem>, HashMap<String, Int>> =
        Pair(
            response.items.map(UpgradeItemMapper),
            response.reagents
        )
}