package ru.lemonapes.dungler.network.models.actions.action_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnemyAttackDataDto(
    @SerialName("enemy_index") val enemyIndex: Int,
    @SerialName("enemy_pure_damage") val enemyPureDamage: Int,
)