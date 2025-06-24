package ru.lemonapes.dungler.network.models.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.network.models.ActionType
import ru.lemonapes.dungler.network.models.actions.action_data.EatingEffectDto
import ru.lemonapes.dungler.network.models.actions.action_data.EnemyAttackDto
import ru.lemonapes.dungler.network.models.actions.action_data.HealEffectDto
import ru.lemonapes.dungler.network.models.actions.action_data.HeroAttackDto
import ru.lemonapes.dungler.network.models.actions.action_data.SkillDataDto

@Serializable
data class ActionDto(
    @SerialName("type") val type: ActionType,
    @SerialName("heal_effect_data") val healEffectData: HealEffectDto? = null,
    @SerialName("hero_attack_data") val heroAttackData: HeroAttackDto? = null,
    @SerialName("enemy_attack_data") val enemyAttackData: EnemyAttackDto? = null,
    @SerialName("eating_effect_data") val eatingEffectData: EatingEffectDto? = null,
    @SerialName("skill_data") val skillDataDto: SkillDataDto? = null,
)