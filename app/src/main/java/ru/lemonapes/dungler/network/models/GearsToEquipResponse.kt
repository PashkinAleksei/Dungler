package ru.lemonapes.dungler.network.models

import kotlinx.serialization.Serializable

@Serializable
data class GearsToEquipResponse(
    val gears: List<ServerGear>? = null,
)