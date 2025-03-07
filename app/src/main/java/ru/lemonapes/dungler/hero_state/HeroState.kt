package ru.lemonapes.dungler.hero_state

import android.icu.util.Calendar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class HeroState(
    val level: Int? = null,
    val health: Int? = null,
    val totalHealth: Int? = null,
    val experience: Int? = null,
    val totalExperience: Int? = null,
    val isLoading: Boolean = true,
    val dungeonState: DungeonState? = null,
    val nextCalcTime: Long = 0,
    val actions: ImmutableList<Action> = persistentListOf(),
) {
    fun calculateActionsRecursiveAndGet(): HeroState {
        if (nextCalcTime == 0L || isLoading) throw HeroStateCalculationException.HeroStateNotInitializedException
        if (Calendar.getInstance().timeInMillis < nextCalcTime) return this

        return if (actions.isNotEmpty() || actions.firstOrNull() !is Action.ActualStateAction) {
            var newHealth = health

            val newActions = actions.toMutableList()
            val action = newActions.removeFirstOrNull()

            action?.let {
                when (action) {
                    is Action.HealAction -> newHealth = newHealth?.let { it + action.data.healAmount }
                    is Action.EnemyAttackAction -> {}
                    is Action.HeroAttackAction -> {}
                    is Action.NextHallAction -> {}

                    is Action.ActualStateAction -> {}
                }
            }
            copy(
                health = newHealth,
                actions = newActions.toPersistentList(),
                nextCalcTime = nextCalcTime + ACTION_TICK_TIME
            ).calculateActionsRecursiveAndGet()
        } else {
            throw HeroStateCalculationException.FinalStateException
        }
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
            )
        const val ACTION_TICK_TIME = 1000L
        const val ACTION_CHECK_TICK_TIME = 100L
    }
}

data class DungeonState(
    val hallNumber: Int? = null,
    val districtStringId: String? = null,
    val dungeonStringId: String? = null,
    //val Enemies: List<Enemy> = emptyList(),
    //val Actions: List<Actions> = emptyList(),
)

sealed class Action {
    data class HealAction(
        val data: EffectData.HealEffectData,
    ) : Action()

    data object NextHallAction : Action()
    data object HeroAttackAction : Action()
    data object EnemyAttackAction : Action()
    data object ActualStateAction : Action()
}

sealed class EffectData() {
    data class HealEffectData(val healAmount: Int) : EffectData()
}

sealed class HeroStateCalculationException(text: String) : Exception(text) {
    data object HeroStateNotInitializedException : HeroStateCalculationException("nextCalcTime is 0") {
        private fun readResolve(): Any = HeroStateNotInitializedException
    }

    data object FinalStateException : HeroStateCalculationException("HeroState is in final state") {
        private fun readResolve(): Any = FinalStateException
    }
}