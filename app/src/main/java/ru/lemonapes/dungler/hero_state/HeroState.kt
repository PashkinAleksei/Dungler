package ru.lemonapes.dungler.hero_state

import android.icu.util.Calendar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.Enemy
import ru.lemonapes.dungler.domain_models.Food

data class HeroState(
    val level: Int? = null,
    val health: Int? = null,
    val totalHealth: Int? = null,
    val experience: Int? = null,
    val totalExperience: Int? = null,
    val isLoading: Boolean = true,
    val dungeonState: DungeonState? = null,
    val isEating: Boolean = false,
    val equippedFood: Food? = null,
    val actions: ImmutableList<Action> = persistentListOf(),
    val nextCalcTime: Long = 0,
) {
    fun calculateActionsRecursiveAndGet(): HeroState {
        if (actions.isEmpty() ||
            actions.firstOrNull() is Action.ActualStateAction ||
            Calendar.getInstance().timeInMillis < nextCalcTime
        ) {
            return this
        }

        val enemies = dungeonState?.enemies ?: persistentListOf()
        var newHealth = health
        var newEquippedFood = equippedFood
        var newDungeonState = dungeonState

        val newActions = actions.toMutableList()
        val action = newActions.removeFirstOrNull()

        action?.let {
            when (action) {
                is Action.HealAction -> {
                    newHealth = health?.let { cHp ->
                        totalHealth?.let { tHp ->
                            val regeneratedHealth = cHp + action.healAmount
                            if (regeneratedHealth < tHp) {
                                regeneratedHealth
                            } else {
                                tHp
                            }
                        }
                    }
                }

                is Action.EatingEffectAction -> {
                    newHealth = health?.let { cHp ->
                        totalHealth?.let { tHp ->
                            val regeneratedHealth = cHp + action.healAmount
                            if (regeneratedHealth < tHp) {
                                regeneratedHealth
                            } else {
                                tHp
                            }
                        }
                    }
                    newEquippedFood = if (action.reduceFood) {
                        equippedFood?.copy(count = equippedFood.count - 1)
                    } else {
                        equippedFood
                    }
                }

                is Action.HeroIsDeadAction -> {
                    newHealth = 1
                    newDungeonState = null
                }

                is Action.EnemyAttackAction -> {
                    newHealth = health?.let { cHp ->
                        val reducedHealth = cHp - action.enemyPureDamage
                        if (reducedHealth > 0) {
                            reducedHealth
                        } else {
                            0
                        }
                    }
                }

                is Action.HeroAttackAction -> {
                    val newEnemies = enemies.mapIndexed { index, enemy ->
                        if (index == action.targetIndex) {
                            val reducedHealth = enemy.health - action.heroPureDamage
                            enemy.copy(health = if (reducedHealth > 0) reducedHealth else 0)
                        } else {
                            enemy
                        }
                    }.toPersistentList()
                    newDungeonState = dungeonState?.copy(enemies = newEnemies)
                }

                is Action.NextHallAction,
                is Action.TakeLootAction,
                is Action.ActualStateAction,
                    -> {
                    //do nothing
                }
            }
        }
        return copy(
            health = newHealth,
            nextCalcTime = nextCalcTime + ACTION_TICK_TIME,
            dungeonState = newDungeonState,
            actions = newActions.toPersistentList(),
            equippedFood = newEquippedFood,
            isEating = newActions.firstOrNull() is Action.EatingEffectAction
        ).calculateActionsRecursiveAndGet()
    }

    companion object {
        val EMPTY
            get() = HeroState()
        val MOCK
            get() = HeroState(
                level = 3,
                health = 123,
                totalHealth = 200,
                experience = 140,
                totalExperience = 250,
                isLoading = false,
                dungeonState = null,
                equippedFood = Food.MOCK_1,
            )
        val EATING_MOCK
            get() = HeroState(
                level = 3,
                health = 123,
                totalHealth = 200,
                experience = 140,
                totalExperience = 250,
                isLoading = false,
                isEating = true,
                dungeonState = DungeonState(),
            )
        const val ACTION_TICK_TIME = 1500L
        const val ACTION_CHECK_TICK_TIME = 400L
    }
}

data class DungeonState(
    val hallNumber: Int? = null,
    val districtStringId: String? = null,
    val dungeonStringId: String? = null,
    val enemies: ImmutableList<Enemy> = persistentListOf(),
)

sealed class Action {
    data class HealAction(
        val healAmount: Int,
    ) : Action()

    data class HeroAttackAction(
        val targetIndex: Int,
        val heroPureDamage: Int,
    ) : Action()

    data class EnemyAttackAction(
        val enemyIndex: Int,
        val enemyPureDamage: Int,
    ) : Action()

    data class EatingEffectAction(
        val healAmount: Int,
        val reduceFood: Boolean,
    ) : Action()

    data object NextHallAction : Action()
    data object TakeLootAction : Action()
    data object HeroIsDeadAction : Action()
    data object ActualStateAction : Action()
}

sealed class HeroStateCalculationException(text: String) : Exception(text) {
    data object HeroStateNotInitializedException : HeroStateCalculationException("nextCalcTime is 0") {
        private fun readResolve(): Any = HeroStateNotInitializedException
    }

    data object FinalStateException : HeroStateCalculationException("HeroState is in final state") {
        private fun readResolve(): Any = FinalStateException
    }
}