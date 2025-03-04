package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.UpgradeGear
import ru.lemonapes.dungler.game_items_data.DEFAULT_GEAR_DATA
import ru.lemonapes.dungler.game_items_data.GEAR_DATA_MAP
import ru.lemonapes.dungler.network.models.ServerUpgradeItem

object UpgradeItemMapper : (ServerUpgradeItem) -> UpgradeGear {
    override fun invoke(item: ServerUpgradeItem): UpgradeGear {
        val gearData = GEAR_DATA_MAP[item.gearId]
        val image =
            gearData?.imageList
                ?.getOrNull(item.level)
                ?: gearData?.imageList?.lastOrNull()
                ?: DEFAULT_GEAR_DATA.imageList.last()

        return UpgradeGear(
            gearId = item.gearId,
            image = image,
            level = item.level,
            stats = item.stats?.toPersistentMap() ?: persistentMapOf(),
            nextStats = item.nextStats?.toPersistentMap() ?: persistentMapOf(),
            reagents = item.reagents?.toPersistentMap() ?: persistentMapOf(),
        )
    }
}