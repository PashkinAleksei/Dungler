package ru.lemonapes.dungler.navigation.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Serializable
enum class GearData(val gearName: Int) {
    @SerialName("green_knight_helm")
    GREEN_KNIGHT_HELM(R.string.green_knight_helm_name),

    @SerialName("green_knight_shoulders")
    GREEN_KNIGHT_SHOULDERS(R.string.green_knight_shoulders_name),

    @SerialName("green_knight_chest")
    GREEN_KNIGHT_CHEST(R.string.green_knight_chest_name),

    @SerialName("green_knight_gloves")
    GREEN_KNIGHT_GLOVES(R.string.green_knight_gloves_name),

    @SerialName("green_knight_legs")
    GREEN_KNIGHT_LEGS(R.string.green_knight_legs_name),

    @SerialName("green_knight_boots")
    GREEN_KNIGHT_BOOTS(R.string.green_knight_boots_name),

    @SerialName("green_knight_sword")
    GREEN_KNIGHT_SWORD(R.string.green_knight_sword_name),
}