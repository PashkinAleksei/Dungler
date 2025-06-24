package ru.lemonapes.dungler.network.models.actions.action_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.AttackResult

@Serializable
data class EnemyAttackDto(
    @SerialName("enemy_index") val enemyIndex: Int,
    @SerialName("pure_damage") val pureDamage: Int,
    @SerialName("attack_result") val attackResult: AttackResult,
)