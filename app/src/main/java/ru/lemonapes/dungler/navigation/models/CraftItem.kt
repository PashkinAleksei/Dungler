package ru.lemonapes.dungler.navigation.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CraftItem(
    @SerialName("gear_id")
    val gearData: GearData,
    @SerialName("gear_type")
    val gearType: GearType,
    val stats: Map<String, Int>,
    val reagents: HashMap<String, Int>
)

@Serializable
data class CraftItemsResponse(
    val items: List<CraftItem>,
    val reagents: HashMap<String, Int>
)
