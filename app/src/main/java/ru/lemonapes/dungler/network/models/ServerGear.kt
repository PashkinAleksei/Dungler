package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.GearId

@Serializable
data class ServerGear(
    @SerialName("gear_id") val gearId: GearId,
    val level: Int,
    val stats: Map<String, Int>,
    @SerialName("is_equipped") val isEquipped: Boolean,
)