package ru.lemonapes.dungler.domain_models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Serializable
enum class FoodId(
    @StringRes val foodName: Int,
    @DrawableRes val image: Int,
) {
    @SerialName("apple_salad")
    APPLE_SALAD(R.string.apple_salad_name, R.drawable.apple_salad),

    @SerialName("monster_leg")
    MONSTER_LEG(R.string.monster_leg_food_name, R.drawable.monster_leg),
}