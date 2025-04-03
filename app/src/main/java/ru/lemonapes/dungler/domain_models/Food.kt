package ru.lemonapes.dungler.domain_models

data class Food(
    val id: FoodId,
    val healthRegenAmount: Int,
    val count: Int,
    val isEquipped: Boolean,
) {
    companion object {
        val MOCK_1
            get() = Food(
                id = FoodId.APPLE_SALAD,
                healthRegenAmount = 3,
                count = 5,
                isEquipped = false
            )
        val MOCK_2
            get() = Food(
                id = FoodId.FRIED_MONSTER_LEG,
                healthRegenAmount = 5,
                count = 15,
                isEquipped = false
            )
    }
}