package ru.lemonapes.dungler.domain_models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
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