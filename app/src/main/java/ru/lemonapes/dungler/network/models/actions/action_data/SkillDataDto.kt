package ru.lemonapes.dungler.network.models.actions.action_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.SkillId

@Serializable
data class SkillDataDto(
    @SerialName("skill_id") val skillId: SkillId,
    @SerialName("data_heroic_strike") val dataHeroicStrike: HeroTargetAttackDto? = null,
    @SerialName("data_swiping_strikes") val dataSwipingStrikes: List<HeroTargetAttackDto>? = null,
    @SerialName("data_whirlwind") val dataWhirlwind: List<HeroTargetAttackDto>? = null,
    @SerialName("cooldown") val cooldown: Int,
)
