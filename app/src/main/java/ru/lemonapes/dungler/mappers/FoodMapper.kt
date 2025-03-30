package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.network.models.ServerFood

object FoodMapper : (ServerFood) -> Food {
    override fun invoke(serverFood: ServerFood) =
        Food(
            id = serverFood.id,
            healthRegenAmount = serverFood.healthRegenAmount,
            count = serverFood.count,
            isEquipped = serverFood.isEquipped
        )
} 