package ru.lemonapes.dungler.network.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.network.models.HeroStateDto
import ru.lemonapes.dungler.network.models.SkillDto

@Serializable
data class SkillsResponse(
    @SerialName("skills") val skills: List<SkillDto>,
    @SerialName("hero_state") val serverHeroState: HeroStateDto,
)
