package ru.lemonapes.dungler.network.models.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.network.models.ActionType
import ru.lemonapes.dungler.network.models.actions.action_data.EatingEffectDataDto
import ru.lemonapes.dungler.network.models.actions.action_data.EnemyAttackDataDto
import ru.lemonapes.dungler.network.models.actions.action_data.HealEffectDataDto
import ru.lemonapes.dungler.network.models.actions.action_data.HeroAttackDataDto
import ru.lemonapes.dungler.network.models.actions.action_data.SkillDataDto

@Serializable
data class ActionDto(
    @SerialName("type") val type: ActionType,
    @SerialName("heal_effect_data") val healEffectData: HealEffectDataDto? = null,
    @SerialName("hero_attack_data") val heroAttackData: HeroAttackDataDto? = null,
    @SerialName("enemy_attack_data") val enemyAttackData: EnemyAttackDataDto? = null,
    @SerialName("eating_effect_data") val eatingEffectData: EatingEffectDataDto? = null,
    @SerialName("skill_data") val skillDataDto: SkillDataDto? = null,
)