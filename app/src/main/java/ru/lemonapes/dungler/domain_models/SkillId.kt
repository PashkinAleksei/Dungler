package ru.lemonapes.dungler.domain_models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Serializable
//Todo: create DTO; fields should be in domain, enum should be in DTO
enum class SkillId(
    @StringRes val skillName: Int,
    @StringRes val skillDescription: Int,
    @DrawableRes val image: Int,
) {
    @SerialName("heroic_strike")
    HEROIC_STRIKE(R.string.heroic_strike_name, R.string.heroic_strike_description, R.drawable.heroic_strike),

    @SerialName("whirlwind")
    WHIRLWIND(R.string.whirlwind_name, R.string.whirlwind_description, R.drawable.whirlwind),

    @SerialName("swiping_strikes")
    SWIPING_STRIKES(R.string.swiping_strikes_name, R.string.swiping_strikes_description, R.drawable.swiping_strikes),
}