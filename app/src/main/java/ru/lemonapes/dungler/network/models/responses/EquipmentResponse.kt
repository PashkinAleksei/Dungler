package ru.lemonapes.dungler.network.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.domain_models.StatId
import ru.lemonapes.dungler.network.models.ServerFood
import ru.lemonapes.dungler.network.models.ServerGear
import ru.lemonapes.dungler.network.models.ServerHeroState

@Serializable
data class EquipmentResponse(
    @SerialName("equipment") val equipment: Equipment? = null,
    @SerialName("stats") val stats: Map<StatId, Int>? = null,
    @SerialName("hero_state") val serverHeroState: ServerHeroState,
)

@Serializable
data class Equipment(
    @SerialName("gears") val gears: Map<GearType, ServerGear>? = null,
    @SerialName("food") val food: ServerFood? = null,
)
