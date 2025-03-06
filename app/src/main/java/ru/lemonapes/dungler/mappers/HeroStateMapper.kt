package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.hero_state.DungeonState
import ru.lemonapes.dungler.hero_state.HeroState

object HeroStateMapper : (ru.lemonapes.dungler.network.models.ServerHeroState) -> HeroState {
    override fun invoke(response: ru.lemonapes.dungler.network.models.ServerHeroState): HeroState {
        return HeroState(
            level = response.level,
            health = response.health,
            totalHealth = response.totalHealth,
            experience = response.experience,
            totalExperience = response.totalExperience,
            isLoading = false,
            dungeonState = response.hallNumber?.let { hallNumber ->
                DungeonState(
                    hallNumber = hallNumber,
                    districtStringId = response.districtStringId,
                    dungeonStringId = response.dungeonStringId
                )
            })
    }
}