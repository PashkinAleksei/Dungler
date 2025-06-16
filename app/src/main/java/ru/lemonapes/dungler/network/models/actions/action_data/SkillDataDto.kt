package ru.lemonapes.dungler.network.models.actions.action_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkillDataDto(
    @SerialName("data_heroic_strike") val dataHeroicStrike: HeroDamageDataDto? = null,
    @SerialName("data_swiping_strikes") val dataSwipingStrikes: List<HeroDamageDataDto>? = null,
    @SerialName("data_whirlwind") val dataWhirlwind: List<HeroDamageDataDto>? = null,
    @SerialName("cooldown") val cooldown: Int,
)
