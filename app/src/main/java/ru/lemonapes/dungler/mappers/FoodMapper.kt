package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.FoodId
import ru.lemonapes.dungler.network.models.ServerFood

object FoodMapper : (ServerFood) -> Food {
    override fun invoke(serverFood: ServerFood) =
        Food(
            id = FoodId.valueOf(serverFood.id.uppercase()),
            healthRegenAmount = serverFood.healthRegenAmount,
            count = serverFood.count
        )
} 