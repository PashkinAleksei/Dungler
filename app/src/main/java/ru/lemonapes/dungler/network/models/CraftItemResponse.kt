package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.navigation.domain_models.GearId
import ru.lemonapes.dungler.navigation.domain_models.GearType

@Serializable
data class CraftItemsResponse(
    val items: List<CraftItem>,
    val reagents: HashMap<String, Int>
)

@Serializable
data class CraftItem(
    @SerialName("gear_id")
    val gearId: GearId,
    @SerialName("gear_type")
    val gearType: GearType,
    val stats: Map<String, Int>,
    val reagents: Map<String, Int>
)
