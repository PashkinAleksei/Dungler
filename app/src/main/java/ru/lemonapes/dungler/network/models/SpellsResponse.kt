package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.SpellId

@Serializable
data class SpellsResponse(
    val spells: List<SpellId>,
    @SerialName("hero_state") val serverHeroState: ServerHeroState,
)
