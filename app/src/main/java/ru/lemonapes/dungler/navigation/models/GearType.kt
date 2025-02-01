package ru.lemonapes.dungler.navigation.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GearType {
    @SerialName("helm")
    Helm,
    @SerialName("shoulders")
    Shoulders,
    @SerialName("chest")
    Chest,
    @SerialName("gloves")
    Gloves,
    @SerialName("legs")
    Legs,
    @SerialName("boots")
    Boots,
    @SerialName("weapon")
    Weapon
}