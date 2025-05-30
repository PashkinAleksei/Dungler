package ru.lemonapes.dungler.network.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.network.models.ServerHeroState

@Serializable
data class SkillsResponse(
    @SerialName("skills") val skills: List<SkillId>,
    @SerialName("hero_state") val serverHeroState: ServerHeroState,
)
