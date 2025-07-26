package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.CreateFood
import ru.lemonapes.dungler.network.models.responses.CreateFoodDto

object CreateFoodMapper : (CreateFoodDto) -> CreateFood {
    override fun invoke(item: CreateFoodDto) = CreateFood(
        foodId = item.foodId,
        healthRegenAmount = item.healthRegenAmount,
        healthRegenTicks = item.healthRegenTicks,
        reagents = item.reagents.toPersistentMap(),
    )
} 