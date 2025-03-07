package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.domain_models.UpgradeGear
import ru.lemonapes.dungler.network.models.ServerUpgradeItem

object UpgradeItemMapper : (ServerUpgradeItem) -> UpgradeGear {
    override fun invoke(item: ServerUpgradeItem): UpgradeGear {
        val image = item.gearId.imageList.getOrNull(item.level)
            ?: item.gearId.imageList.lastOrNull()
            ?: GearId.UNKNOWN_ITEM.imageList.last()

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