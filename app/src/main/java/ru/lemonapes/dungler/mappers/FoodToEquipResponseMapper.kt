package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.network.models.FoodToEquipResponse

object FoodToEquipResponseMapper : (FoodToEquipResponse) -> ImmutableList<Food> {
    override fun invoke(response: FoodToEquipResponse) =
        response.food.map(FoodMapper).toPersistentList()
} 