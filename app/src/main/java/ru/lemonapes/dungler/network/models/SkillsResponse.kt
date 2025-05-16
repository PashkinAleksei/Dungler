package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.SkillId

@Serializable
data class SkillsResponse(
    @SerialName("spells") val skills: List<SkillId>,
    @SerialName("hero_state") val serverHeroState: ServerHeroState,
)
