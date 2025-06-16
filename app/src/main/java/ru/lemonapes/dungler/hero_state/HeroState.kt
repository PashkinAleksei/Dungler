package ru.lemonapes.dungler.hero_state

import android.icu.util.Calendar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.Enemy
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.SkillsEquipment

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
    val skillsEquipment: SkillsEquipment = SkillsEquipment.MOCK,
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
        var newSkillsEquipment = skillsEquipment

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

                //HeroAttackAction
                is Action.HeroAttackAction.Common -> {
                    val newEnemies = enemies.applyDamageToEnemies(listOf(action.damageData))
                    newDungeonState = dungeonState?.copy(enemies = newEnemies)
                }

                is Action.HeroAttackAction.ModifierSwipingStrikes -> {
                    val newEnemies = enemies.applyDamageToEnemies(action.damageData)
                    newDungeonState = dungeonState?.copy(enemies = newEnemies)
                }

                //SkillAction
                is Action.SkillAction.SwipingStrikes -> {
                    val newEnemies = enemies.applyDamageToEnemies(action.damageData)
                    newDungeonState = dungeonState?.copy(enemies = newEnemies)
                    newSkillsEquipment = newSkillsEquipment.copyWithDeactivateSkill(action.skillId)
                }

                is Action.SkillAction.Whirlwind -> {
                    val newEnemies = enemies.applyDamageToEnemies(action.damageData)
                    newDungeonState = dungeonState?.copy(enemies = newEnemies)
                    newSkillsEquipment = newSkillsEquipment.copyWithDeactivateSkill(action.skillId)
                }

                is Action.SkillAction.HeroicStrike -> {
                    val newEnemies = enemies.applyDamageToEnemies(listOf(action.damageData))
                    newDungeonState = dungeonState?.copy(enemies = newEnemies)
                    newSkillsEquipment = newSkillsEquipment.copyWithDeactivateSkill(action.skillId)
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
            skillsEquipment = newSkillsEquipment,
            isEating = newActions.firstOrNull() is Action.EatingEffectAction
        ).calculateActionsRecursiveAndGet()
    }

    private fun List<Enemy>.applyDamageToEnemies(damageDataList: List<HeroDamageData>): ImmutableList<Enemy> =
        mapIndexed { index, enemy ->
            val damageData = damageDataList.firstOrNull { it.targetIndex == index }
            if (damageData != null) {
                val reducedHealth = enemy.health - damageData.heroPureDamage
                enemy.copy(health = if (reducedHealth > 0) reducedHealth else 0)
            } else {
                enemy
            }
        }.toPersistentList()

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

sealed class HeroStateCalculationException(text: String) : Exception(text) {
    data object HeroStateNotInitializedException : HeroStateCalculationException("nextCalcTime is 0") {
        private fun readResolve(): Any = HeroStateNotInitializedException
    }

    data object FinalStateException : HeroStateCalculationException("HeroState is in final state") {
        private fun readResolve(): Any = FinalStateException
    }
}