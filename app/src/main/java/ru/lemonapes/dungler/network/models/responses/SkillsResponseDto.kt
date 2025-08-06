package ru.lemonapes.dungler.network.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.Skill
import ru.lemonapes.dungler.network.models.HeroStateDto

@Serializable
data class SkillsResponseDto(
    @SerialName("skills") val skills: List<Skill>,
    @SerialName("battle_state") val serverHeroState: HeroStateDto,
)
