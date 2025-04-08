package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerAction(
    @SerialName("type") val type: ActionType,
    @SerialName("heal_effect_data") val healEffectData: HealEffectData? = null,
    @SerialName("hero_attack_data") val heroAttackData: HeroAttackData? = null,
    @SerialName("enemy_attack_data") val enemyAttackData: EnemyAttackData? = null,
    @SerialName("eating_effect_data") val eatingEffectData: EatingEffectData? = null,
)

@Serializable
data class HeroAttackData(
    @SerialName("hero_pure_damage") val heroPureDamage: Int,
    @SerialName("target_index") val targetIndex: Int,
)

@Serializable
data class EnemyAttackData(
    @SerialName("enemy_index") val enemyIndex: Int,
    @SerialName("enemy_pure_damage") val enemyPureDamage: Int,
)

@Serializable
data class HealEffectData(
    @SerialName("heal_amount") val healAmount: Int,
)

@Serializable
data class EatingEffectData(
    @SerialName("heal_amount") val healAmount: Int,
    @SerialName("reduce_food") val reduceFood: Boolean,
)