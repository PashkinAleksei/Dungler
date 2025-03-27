package ru.lemonapes.dungler.domain_models

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf

data class CreateFood(
    val foodId: FoodId,
    val healthRegenAmount: Int,
    val reagents: ImmutableMap<ReagentId, Int>,
) {
    companion object {
        fun getMock() = CreateFood(
            foodId = FoodId.APPLE_SALAD,
            healthRegenAmount = 3,
            reagents = persistentMapOf(
                ReagentId.APPLE to 1
            )
        )
    }
} 