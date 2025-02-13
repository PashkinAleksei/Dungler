package ru.lemonapes.dungler.domain_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Gear(
    @SerialName("name") val gearId: GearId,
    val level: Int,
    val stats: Map<String, Int>,
    @SerialName("is_equipped") val isEquipped: Boolean,
) {
    companion object {
        val EMPTY
            get() = Gear(
                gearId = GearId.GREEN_KNIGHT_CHEST,
                level = 3,
                stats = mapOf("stamina" to 3),
                isEquipped = false
            )
    }
}