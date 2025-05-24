package ru.lemonapes.dungler.network.models.responses

import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.network.models.ServerFood
import ru.lemonapes.dungler.network.models.ServerGear

@Serializable
data class InventoryResponse(
    val gears: List<ServerGear>? = null,
    val food: List<ServerFood>? = null,
    val reagents: Map<ReagentId, Int>? = null,
)