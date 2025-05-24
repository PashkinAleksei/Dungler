package ru.lemonapes.dungler.network.models.responses

import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.network.models.ServerFood

@Serializable
data class FoodToEquipResponse(
    val food: List<ServerFood>? = null,
) 