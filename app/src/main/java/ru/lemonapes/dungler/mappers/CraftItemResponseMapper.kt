package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import ru.lemonapes.dungler.domain_models.CreateGear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.domain_models.UpgradeGear
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.network.models.CraftItemsResponse

object CraftItemResponseMapper : (CraftItemsResponse) ->
CraftItemMapperResponse {
    override fun invoke(response: CraftItemsResponse) =
        CraftItemMapperResponse(
            createItems = response.createItems?.map(CraftItemMapper)?.toPersistentList() ?: persistentListOf(),
            upgradeItems = response.upgradeItems?.map(UpgradeItemMapper)?.toPersistentList() ?: persistentListOf(),
            reagents = response.reagents?.toPersistentMap() ?: persistentMapOf(),
            heroState = HeroStateMapper(response.serverHeroState),
        )
}

data class CraftItemMapperResponse(
    val createItems: ImmutableList<CreateGear>,
    val upgradeItems: ImmutableList<UpgradeGear>,
    val reagents: ImmutableMap<ReagentId, Int>,
    val heroState: HeroState,
)