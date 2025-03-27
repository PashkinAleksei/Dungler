package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.CreateFood
import ru.lemonapes.dungler.network.models.ServerCreateFood

object CreateFoodMapper : (ServerCreateFood) -> CreateFood {
    override fun invoke(item: ServerCreateFood) = CreateFood(
        foodId = item.foodId,
        healthRegenAmount = item.healthRegenAmount,
        reagents = item.reagents?.toPersistentMap() ?: persistentMapOf(),
    )
} 