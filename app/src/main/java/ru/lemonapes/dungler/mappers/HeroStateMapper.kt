package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.hero_state.DungeonState
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.network.models.HeroStateResponce

object HeroStateMapper : (HeroStateResponce) -> HeroState {
    override fun invoke(response: HeroStateResponce): HeroState {
        return HeroState(
            level = response.level,
            health = response.health,
            totalHealth = response.totalHealth,
            experience = response.experience,
            isLoading = false,
            dungeonState = DungeonState(
                hallNumber = response.hallNumber,
                districtStringId = response.districtStringId,
                dungeonStringId = response.dungeonStringId
            )
        )
    }
}