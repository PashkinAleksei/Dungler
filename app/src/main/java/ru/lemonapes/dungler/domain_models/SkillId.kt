package ru.lemonapes.dungler.domain_models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Serializable
enum class SkillId(
    @StringRes val skillName: Int,
    @StringRes val skillDescription: Int,
    @DrawableRes val image: Int,
) {
    @SerialName("heroic_strike")
    HEROIC_STRIKE(R.string.heroic_strike_name, R.string.heroic_strike_description, R.drawable.heroic_strike),

    @SerialName("whirlwind")
    WHIRLWIND(R.string.whirlwind_name, R.string.whirlwind_description, R.drawable.whirlwind),
}