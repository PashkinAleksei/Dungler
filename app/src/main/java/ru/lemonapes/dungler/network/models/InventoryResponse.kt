package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.ReagentId

@Serializable
data class InventoryResponse(
    @SerialName("hero_id") val heroId: Int,
    val gears: List<ServerGear>,
    val reagents: Map<ReagentId, Int>,
)