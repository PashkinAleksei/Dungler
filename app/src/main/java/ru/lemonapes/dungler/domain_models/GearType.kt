package ru.lemonapes.dungler.domain_models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
enum class GearType(val serialName: String) {
    @SerialName("helm")
    HELM("helm"),

    @SerialName("shoulders")
    SHOULDERS("shoulders"),

    @SerialName("chest")
    CHEST("chest"),

    @SerialName("gloves")
    GLOVES("gloves"),

    @SerialName("legs")
    LEGS("legs"),

    @SerialName("boots")
    BOOTS("boots"),

    @SerialName("weapon")
    WEAPON("weapon"),

    @SerialName("unknown")
    UNKNOWN("unknown")
}