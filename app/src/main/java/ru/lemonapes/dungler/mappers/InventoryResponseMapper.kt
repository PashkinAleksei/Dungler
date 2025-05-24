package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.network.models.responses.InventoryResponse

object InventoryResponseMapper :
        (InventoryResponse) -> Triple<ImmutableList<Gear>, ImmutableList<Food>, ImmutableMap<ReagentId, Int>> {
    override fun invoke(response: InventoryResponse) =
        Triple(
            response.gears?.map(GearMapper)?.toPersistentList() ?: persistentListOf(),
            response.food?.map(FoodMapper)?.toPersistentList() ?: persistentListOf(),
            response.reagents?.toPersistentMap() ?: persistentMapOf()
        )
}