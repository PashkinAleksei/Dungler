package ru.lemonapes.dungler.domain_models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Serializable
enum class ReagentId(
    @StringRes val reagentName: Int,
    @DrawableRes val image: Int,
) {
    @SerialName("linen_cloth")
    LINEN_CLOTH(R.string.linen_cloth_name, R.drawable.linen_cloth),

    @SerialName("copper")
    COPPER(R.string.copper_name, R.drawable.copper),

    @SerialName("topaz")
    TOPAZ(R.string.topaz_name, R.drawable.topaz),

    @SerialName("apple")
    APPLE(R.string.apple_name, R.drawable.apple),
}