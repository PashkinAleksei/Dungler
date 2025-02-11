package ru.lemonapes.dungler.navigation.domain_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GearType {
    @SerialName("helm")
    HELM,
    @SerialName("shoulders")
    SHOULDERS,
    @SerialName("chest")
    CHEST,
    @SerialName("gloves")
    GLOVES,
    @SerialName("legs")
    LEGS,
    @SerialName("boots")
    BOOTS,
    @SerialName("weapon")
    WEAPON
}