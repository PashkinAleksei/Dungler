package ru.lemonapes.dungler.navigation.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Serializable
enum class GearData(val gearId: String, val gearName: Int) {
    @SerialName("green_knight_helm")
    GREEN_KNIGHT_HELM("green_knight_helm", R.string.green_knight_helm_name),

    @SerialName("green_knight_shoulders")
    GREEN_KNIGHT_SHOULDERS("green_knight_shoulders", R.string.green_knight_shoulders_name),

    @SerialName("green_knight_chest")
    GREEN_KNIGHT_CHEST("green_knight_chest", R.string.green_knight_chest_name),

    @SerialName("green_knight_gloves")
    GREEN_KNIGHT_GLOVES("green_knight_gloves", R.string.green_knight_gloves_name),

    @SerialName("green_knight_legs")
    GREEN_KNIGHT_LEGS("green_knight_legs", R.string.green_knight_legs_name),

    @SerialName("green_knight_boots")
    GREEN_KNIGHT_BOOTS("green_knight_boots", R.string.green_knight_boots_name),

    @SerialName("green_knight_sword")
    GREEN_KNIGHT_SWORD("green_knight_sword", R.string.green_knight_sword_name),
}