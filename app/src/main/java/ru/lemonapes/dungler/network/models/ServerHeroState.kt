package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerHeroStateResponse(
    @SerialName("hero_state") val serverHeroState: ServerHeroState,
)

@Serializable
data class ServerHeroState(
    @SerialName("level") val level: Int? = null,
    @SerialName("health") val health: Int? = null,
    @SerialName("total_health") val totalHealth: Int? = null,
    @SerialName("experience") val experience: Int? = null,
    @SerialName("total_experience") val totalExperience: Int? = null,
    @SerialName("hall_number") val hallNumber: Int? = null,
    @SerialName("district_string_id") val districtStringId: String? = null,
    @SerialName("dungeon_string_id") val dungeonStringId: String? = null,
    @SerialName("actions") val actions: List<ServerAction> = emptyList(),

    // @SerialName("enemies") val Enemies: List<Enemy> = emptyList(),
    // @SerialName("actions") val Actions: List<Actions> = emptyList(),
)

@Serializable
data class ServerAction(
    @SerialName("type") val type: ActionType,
    @SerialName("heal_effect_data") val healEffectData: Map<String, Int>? = null,
)

@Serializable
enum class ActionType {
    NEXT_HALL,
    HEAL_EFFECT,
    HERO_ATTACK,
    ENEMY_ATTACK,
    ACTUAL_STATE,
}