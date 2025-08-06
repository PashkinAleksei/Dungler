package ru.lemonapes.dungler.mappers

import android.icu.util.Calendar
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.actions.Action
import ru.lemonapes.dungler.hero_state.DungeonState
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.hero_state.HeroState.Companion.ACTION_TICK_TIME
import ru.lemonapes.dungler.network.models.HeroStateDto
import ru.lemonapes.dungler.network.models.ServerHeroStateResponse

object HeroStateMapper : (HeroStateDto, Action?) -> HeroState {
    override fun invoke(response: HeroStateDto, lastExecutedAction: Action?): HeroState {
        return HeroState(
            level = response.level,
            health = response.health,
            totalHealth = response.totalHealth,
            experience = response.experience,
            totalExperience = response.totalExperience,
            isLoading = false,
            equippedFood = response.equippedFood?.let { FoodMapper(it) },
            skillsEquipment = SkillsEquipmentMapper(response.skillsEquipment),
            dungeonState = response.hallNumber?.let { hallNumber ->
                DungeonState(
                    hallNumber = hallNumber,
                    districtStringId = response.districtStringId,
                    dungeonStringId = response.dungeonStringId,
                    enemies = response.enemies.map(EnemyMapper).toPersistentList()
                )
            },
            actions = response.actions.toPersistentList(),
            lastExecutedAction = lastExecutedAction,
            nextCalcTime = Calendar.getInstance().timeInMillis + ACTION_TICK_TIME
        )
    }
}

object HeroStateResponseMapper : (ServerHeroStateResponse, Action?) -> HeroState {
    override fun invoke(response: ServerHeroStateResponse, lastExecutedAction: Action?): HeroState {
        return HeroStateMapper(response.serverHeroState, lastExecutedAction)
    }
}
