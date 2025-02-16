package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.network.models.GearsToEquipResponse

object GearsToEquipResponseMapper : (GearsToEquipResponse) -> ImmutableList<Gear> {
    override fun invoke(response: GearsToEquipResponse) =
        response.gears?.map(GearMapper)?.toPersistentList() ?: persistentListOf()
}