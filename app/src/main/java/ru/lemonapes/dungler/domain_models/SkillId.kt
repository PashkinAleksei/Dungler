package ru.lemonapes.dungler.domain_models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Serializable
enum class SkillId(
    @StringRes val skillName: Int,
    @StringRes val skillDescription: Int,
    @DrawableRes val image: Int,
) {
    HEROIC_STRIKE(R.string.heroic_strike_name, R.string.heroic_strike_description, R.drawable.heroic_strike),
    WHIRLWIND(R.string.whirlwind_name, R.string.whirlwind_description, R.drawable.whirlwind),
    SWIPING_STRIKES(R.string.swiping_strikes_name, R.string.swiping_strikes_description, R.drawable.swiping_strikes),
}