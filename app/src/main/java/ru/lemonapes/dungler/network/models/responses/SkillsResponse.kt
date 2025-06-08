package ru.lemonapes.dungler.network.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.network.models.ServerHeroState
import ru.lemonapes.dungler.network.models.SkillDto

@Serializable
data class SkillsResponse(
    @SerialName("skills") val skills: List<SkillDto>,
    @SerialName("hero_state") val serverHeroState: ServerHeroState,
)
