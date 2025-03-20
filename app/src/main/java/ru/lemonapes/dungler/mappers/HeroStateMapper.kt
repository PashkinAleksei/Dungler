package ru.lemonapes.dungler.mappers

import android.icu.util.Calendar
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.hero_state.Action
import ru.lemonapes.dungler.hero_state.DungeonState
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.hero_state.HeroState.Companion.ACTION_TICK_TIME
import ru.lemonapes.dungler.network.models.ActionType

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
                    dungeonStringId = response.dungeonStringId,
                    enemies = response.enemies.map(EnemyMapper).toPersistentList(),
                )
            },
            actions = response.actions.map {
                when (it.type) {
                    ActionType.HEAL_EFFECT ->
                        Action.HealAction(
                            healAmount = it.healEffectData?.healAmount ?: 0,
                        )

                    ActionType.HERO_ATTACK -> Action.HeroAttackAction(
                        targetIndex = it.heroAttackData?.targetIndex ?: 0,
                        heroPureDamage = it.heroAttackData?.heroPureDamage ?: 0,
                    )

                    ActionType.ENEMY_ATTACK -> Action.EnemyAttackAction(
                        enemyIndex = it.enemyAttackData?.enemyIndex ?: 0,
                        enemyPureDamage = it.enemyAttackData?.enemyPureDamage ?: 0,
                    )

                    ActionType.NEXT_HALL -> Action.NextHallAction
                    ActionType.TAKE_LOOT -> Action.TakeLootAction
                    ActionType.HERO_IS_DEAD -> Action.HeroIsDeadAction
                    ActionType.ACTUAL_STATE -> Action.ActualStateAction
                }
            }.toPersistentList(),
            nextCalcTime = Calendar.getInstance().timeInMillis + ACTION_TICK_TIME
        )
    }
}

object HeroStateResponseMapper : (ru.lemonapes.dungler.network.models.ServerHeroStateResponse) -> HeroState {
    override fun invoke(response: ru.lemonapes.dungler.network.models.ServerHeroStateResponse): HeroState {
        return HeroStateMapper(response.serverHeroState)
    }
}