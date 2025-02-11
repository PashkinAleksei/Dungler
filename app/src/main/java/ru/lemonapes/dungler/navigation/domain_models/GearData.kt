package ru.lemonapes.dungler.navigation.domain_models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

data class GearData(
    @StringRes val name: Int,
    @DrawableRes val image: Int,
)


@Serializable
enum class GearId(val gearId: String) {
    @SerialName("green_knight_helm")
    GREEN_KNIGHT_HELM("green_knight_helm"),

    @SerialName("green_knight_shoulders")
    GREEN_KNIGHT_SHOULDERS("green_knight_shoulders"),

    @SerialName("green_knight_chest")
    GREEN_KNIGHT_CHEST("green_knight_chest"),

    @SerialName("green_knight_gloves")
    GREEN_KNIGHT_GLOVES("green_knight_gloves"),

    @SerialName("green_knight_legs")
    GREEN_KNIGHT_LEGS("green_knight_legs"),

    @SerialName("green_knight_boots")
    GREEN_KNIGHT_BOOTS("green_knight_boots"),

    @SerialName("green_knight_sword")
    GREEN_KNIGHT_SWORD("green_knight_sword"),
}