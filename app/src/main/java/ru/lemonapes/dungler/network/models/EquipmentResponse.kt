package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.GearType

@Serializable
data class EquipmentResponse(
    @SerialName("equipment") val equipment: Map<GearType, ServerGear>,
    @SerialName("stats") val stats: Map<String, Int>,
)
