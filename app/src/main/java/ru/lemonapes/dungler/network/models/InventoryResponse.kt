package ru.lemonapes.dungler.network.models

import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.ReagentId

@Serializable
data class InventoryResponse(
    val gears: List<ServerGear>? = null,
    val reagents: Map<ReagentId, Int>? = null,
)