package ru.lemonapes.dungler.navigation.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CraftItem(
    val name: String,
    @SerialName("gear_type")
    val gearType: String,
    val stats: Map<String, Int>,
    val reagents: Map<String, Int>
)

@Serializable
data class CraftItemsResponse(
    val items: List<CraftItem>,
    val reagents: Map<String, Int>
)
