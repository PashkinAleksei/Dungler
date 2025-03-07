package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.network.models.ServerGear

object GearMapper : (ServerGear) -> Gear {
    override fun invoke(item: ServerGear): Gear {
        val image = item.gearId.imageList.getOrNull(item.level)
            ?: item.gearId.imageList.lastOrNull()
            ?: GearId.UNKNOWN_ITEM.imageList.last()

        return Gear(
            gearId = item.gearId,
            image = image,
            level = item.level,
            stats = item.stats.toPersistentMap(),
            isEquipped = item.isEquipped
        )
    }
}