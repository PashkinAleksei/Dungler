package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.CreateGear
import ru.lemonapes.dungler.navigation.game_items_data.DEFAULT_GEAR_DATA
import ru.lemonapes.dungler.navigation.game_items_data.GEAR_DATA_MAP
import ru.lemonapes.dungler.network.models.ServerCreateItem

object CraftItemMapper : (ServerCreateItem) -> CreateGear {
    override fun invoke(craftItem: ServerCreateItem): CreateGear {
        val gearData = GEAR_DATA_MAP[craftItem.gearId]
        val image = gearData?.imageList?.firstOrNull()
            ?: DEFAULT_GEAR_DATA.imageList.first()

        return CreateGear(
            gearId = craftItem.gearId,
            image = image,
            stats = craftItem.stats.toPersistentMap(),
            reagents = craftItem.reagents.toPersistentMap(),
        )
    }
}