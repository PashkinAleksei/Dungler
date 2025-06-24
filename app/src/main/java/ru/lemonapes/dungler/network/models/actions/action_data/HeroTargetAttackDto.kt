package ru.lemonapes.dungler.network.models.actions.action_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.AttackResult

@Serializable
data class HeroTargetAttackDto(
    @SerialName("pure_damage") val heroPureDamage: Int,
    @SerialName("target_index") val targetIndex: Int,
    @SerialName("attack_result") val attackResult: AttackResult,
)