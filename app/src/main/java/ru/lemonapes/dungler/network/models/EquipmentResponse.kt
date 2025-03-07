package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.domain_models.StatId

@Serializable
data class EquipmentResponse(
    @SerialName("equipment") val equipment: Map<GearType, ServerGear>? = null,
    @SerialName("stats") val stats: Map<StatId, Int>? = null,
    @SerialName("hero_state") val serverHeroState: ServerHeroState,
)
