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
    @SerialName("enemies") val enemies: List<ServerEnemy> = emptyList(),

    // @SerialName("enemies") val Enemies: List<Enemy> = emptyList(),
    // @SerialName("actions") val Actions: List<Actions> = emptyList(),
)

@Serializable
enum class ActionType {
    NEXT_HALL,
    HEAL_EFFECT,
    HERO_IS_DEAD,
    HERO_ATTACK,
    ENEMY_ATTACK,
    ACTUAL_STATE,
}