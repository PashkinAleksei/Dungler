package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.SpellId
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.network.models.SpellsResponse

object SpellsResponseMapper : (SpellsResponse) -> SpellsMapperResponse {
    override fun invoke(response: SpellsResponse) =
        SpellsMapperResponse(
            spells = response.spells.toPersistentList(),
            heroState = HeroStateMapper(response.serverHeroState)
        )
}

data class SpellsMapperResponse(
    val spells: ImmutableList<SpellId>,
    val heroState: HeroState,
)
