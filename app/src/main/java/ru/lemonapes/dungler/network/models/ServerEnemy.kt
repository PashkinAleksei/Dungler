package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.EnemyTypeId

@Serializable
data class ServerEnemy(
    @SerialName("enemy_type") val enemyTypeId: EnemyTypeId = EnemyTypeId.UNKNOWN_ENEMY,
    @SerialName("current_health") val currentHealth: Int,
    @SerialName("damage_min") val damageMin: Int,
    @SerialName("damage_max") val damageMax: Int,
    @SerialName("max_health") val maxHealth: Int,
    @SerialName("level") val level: Int,
)
