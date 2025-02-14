package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.navigation.game_items_data.DEFAULT_GEAR_DATA
import ru.lemonapes.dungler.navigation.game_items_data.GEAR_DATA_MAP
import ru.lemonapes.dungler.network.models.ServerGear

object GearMapper : (ServerGear) -> Gear {
    override fun invoke(item: ServerGear): Gear {
        val gearData = GEAR_DATA_MAP[item.gearId]
        val image =
            gearData?.imageList
                ?.getOrNull(item.level)
                ?: gearData?.imageList?.lastOrNull()
                ?: DEFAULT_GEAR_DATA.imageList.last()

        return Gear(
            gearId = item.gearId,
            image = image,
            level = item.level,
            stats = item.stats.toPersistentMap(),
            isEquipped = item.isEquipped
        )
    }
}