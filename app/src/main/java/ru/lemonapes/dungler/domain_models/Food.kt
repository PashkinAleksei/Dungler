package ru.lemonapes.dungler.domain_models

data class Food(
    val id: FoodId,
    val healthRegenAmount: Int,
    val count: Int,
) {
    companion object {
        val MOCK
            get() = Food(
                id = FoodId.APPLE_SALAD,
                healthRegenAmount = 10,
                count = 5
            )
    }
}