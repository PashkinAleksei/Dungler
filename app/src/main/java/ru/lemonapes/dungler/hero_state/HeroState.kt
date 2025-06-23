package ru.lemonapes.dungler.hero_state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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
    val lastExecutedAction: Action? = null,
    val nextCalcTime: Long = 0,
) {
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
                lastExecutedAction = Action.HealAction(10)
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
                lastExecutedAction = Action.EatingEffectAction(10, true),
                dungeonState = DungeonState(),
            )
        const val ACTION_TICK_TIME = 1500L
        const val ACTION_CHECK_TICK_TIME = 400L
    }

    fun getLastDamageToEnemy(enemyIndex: Int): Int? {
        return when (lastExecutedAction) {
            is Action.SingleDamage -> {
                val damageData = lastExecutedAction.damageData
                damageData.heroPureDamage.takeIf { damageData.targetIndex == enemyIndex }
            }

            is Action.MassiveDamage -> {
                lastExecutedAction.damageData.firstOrNull { it.targetIndex == enemyIndex }?.heroPureDamage
            }

            else -> null
        }
    }

    fun getLastHeroHPChange(): Int? {
        return when (lastExecutedAction) {
            is Action.EnemyAttackAction -> {
                -lastExecutedAction.enemyPureDamage
            }

            is Action.HealAction -> {
                lastExecutedAction.healAmount
            }

            is Action.EatingEffectAction -> {
                lastExecutedAction.healAmount
            }

            else -> null
        }
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