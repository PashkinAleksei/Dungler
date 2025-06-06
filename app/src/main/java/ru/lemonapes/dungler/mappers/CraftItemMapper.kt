package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.CreateGear
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.network.models.responses.ServerCreateItem

object CraftItemMapper : (ServerCreateItem) -> CreateGear {
    override fun invoke(craftItem: ServerCreateItem): CreateGear {
        val image = craftItem.gearId.imageList.firstOrNull()
            ?: GearId.UNKNOWN_ITEM.imageList.first()

        return CreateGear(
            gearId = craftItem.gearId,
            image = image,
            stats = craftItem.stats?.toPersistentMap() ?: persistentMapOf(),
            reagents = craftItem.reagents?.toPersistentMap() ?: persistentMapOf(),
        )
    }
}