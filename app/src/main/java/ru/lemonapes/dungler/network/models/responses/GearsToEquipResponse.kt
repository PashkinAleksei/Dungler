package ru.lemonapes.dungler.network.models.responses

import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.network.models.ServerGear

@Serializable
data class GearsToEquipResponse(
    val gears: List<ServerGear>? = null,
)