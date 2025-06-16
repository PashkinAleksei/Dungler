package ru.lemonapes.dungler.network.models.actions.action_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroAttackDataDto(
    @SerialName("hero_common_attack_data") val heroCommonAttackData: HeroDamageDataDto? = null,
    @SerialName("modifier_swiping_strikes_data") val modifierSwipingStrikesData: List<HeroDamageDataDto>? = null,
)