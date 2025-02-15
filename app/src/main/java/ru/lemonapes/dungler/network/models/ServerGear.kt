package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.domain_models.StatId

@Serializable
data class ServerGear(
    @SerialName("gear_id") val gearId: GearId,
    val level: Int,
    val stats: Map<StatId, Int>,
    @SerialName("is_equipped") val isEquipped: Boolean,
)