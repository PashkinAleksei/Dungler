package ru.lemonapes.dungler.mappers

import android.icu.util.Calendar
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.hero_state.Action
import ru.lemonapes.dungler.hero_state.DungeonState
import ru.lemonapes.dungler.hero_state.EffectData
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.hero_state.HeroState.Companion.ACTION_TICK_TIME
import ru.lemonapes.dungler.network.models.ActionType

object HeroStateMapper : (ru.lemonapes.dungler.network.models.ServerHeroState) -> HeroState {
    override fun invoke(response: ru.lemonapes.dungler.network.models.ServerHeroState): HeroState {
        return HeroState(
            level = response.level,
            health = response.health,
            totalHealth = response.totalHealth,
            actions = response.actions.map {
                when (it.type) {
                    ActionType.HEAL_EFFECT ->
                        Action.HealAction(
                            EffectData.HealEffectData(it.healEffectData?.get("heal_amount") ?: 0)
                        )

                    ActionType.NEXT_HALL -> Action.NextHallAction
                    ActionType.HERO_IS_DEAD -> Action.HeroIsDeadAction
                    ActionType.HERO_ATTACK -> Action.HeroAttackAction
                    ActionType.ENEMY_ATTACK -> Action.EnemyAttackAction
                    ActionType.ACTUAL_STATE -> Action.ActualStateAction
                }
            }.toPersistentList(),
            enemies = response.enemies.map(EnemyMapper).toPersistentList(),
            experience = response.experience,
            totalExperience = response.totalExperience,
            isLoading = false,
            dungeonState = response.hallNumber?.let { hallNumber ->
                DungeonState(
                    hallNumber = hallNumber,
                    districtStringId = response.districtStringId,
                    dungeonStringId = response.dungeonStringId
                )
            },
            nextCalcTime = Calendar.getInstance().timeInMillis + ACTION_TICK_TIME
        )
    }
}

object HeroStateResponseMapper : (ru.lemonapes.dungler.network.models.ServerHeroStateResponse) -> HeroState {
    override fun invoke(response: ru.lemonapes.dungler.network.models.ServerHeroStateResponse): HeroState {
        return HeroStateMapper(response.serverHeroState)
    }
}