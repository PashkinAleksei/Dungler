package ru.lemonapes.dungler.network.models

import kotlinx.serialization.Serializable

@Serializable
data class FoodToEquipResponse(
    val food: List<ServerFood>? = null,
) 