package ru.lemonapes.dungler.repositories

import android.icu.util.Calendar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.Utils.Companion.log
import ru.lemonapes.dungler.di.ApplicationScope
import ru.lemonapes.dungler.domain_models.Enemy
import ru.lemonapes.dungler.hero_state.Action
import ru.lemonapes.dungler.hero_state.DungeonState
import ru.lemonapes.dungler.hero_state.HeroDamageData
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.hero_state.HeroState.Companion.ACTION_CHECK_TICK_TIME
import ru.lemonapes.dungler.hero_state.HeroState.Companion.ACTION_TICK_TIME
import ru.lemonapes.dungler.mappers.HeroStateResponseMapper
import ru.lemonapes.dungler.network.endpoints.getHeroState
import ru.lemonapes.dungler.stores.HeroStateStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroStateRepository @Inject constructor(
    private val heroStateStore: HeroStateStore,
    @ApplicationScope private val coroutineScope: CoroutineScope,
) {

    val heroStateFlow
        get() = heroStateStore.heroStateFlow

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        log("$throwable")
        throwable.printStackTrace()
    }

    private val pollingExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        log("$throwable")
        throwable.printStackTrace()
        startPolling(DelayOption.RETRY)
    }

    @Volatile
    private var isActionCountingActive = true


    private var pollingJob: Job? = null
    private var actionCalculationJob: Job? = null

    private var polingRetryCounter = 0

    @OptIn(ExperimentalCoroutinesApi::class)
    fun startPolling(delayOption: DelayOption = DelayOption.NORMAL) {
        //log("startPolling coroutineScope")
        pollingJob?.cancel()
        pollingJob = coroutineScope.launch(Dispatchers.IO + pollingExceptionHandler) {
            when (delayOption) {
                DelayOption.DELAY -> delay(POLLING_INTERVAL)
                DelayOption.RETRY -> {
                    polingRetryCounter++
                    if (polingRetryCounter > 3) {
                        delay(POLLING_RETRY_DELAY)
                    }
                }

                DelayOption.NORMAL -> {
                    //Do Nothing
                }
            }

            while (isActive) {
                fetchHeroState()
                polingRetryCounter = 0
                delay(POLLING_INTERVAL)
            }
        }
    }

    val lastExecutedAction get() = heroStateFlow.value.lastExecutedAction

    private fun resetPolling() {
        startPolling()
    }

    private fun delayPolling() {
        startPolling(DelayOption.DELAY)
    }

    fun stopPolling() {
        //log("stopPolling")
        pollingJob?.cancel()
    }

    private suspend fun fetchHeroState() {
        //log("start fetching HeroState")
        heroStateStore.heroState = HeroStateResponseMapper(getHeroState(), lastExecutedAction)
        resumeActionsCalculation()
        log("HeroState fetched ${heroStateStore.heroState}")
    }

    fun heroInDungeonError() {
        heroStateStore.heroState = heroStateStore.heroState.copy(
            isLoading = true,
            dungeonState = DungeonState()
        )
        resetPolling()
    }

    fun setNewHeroState(newHeroState: HeroState) {
        log("setNewHeroState $newHeroState")
        heroStateStore.heroState = newHeroState
        delayPolling()
        resumeActionsCalculation()
    }

    //-----------------------------------------------------------------------------------------------------------------
    fun startActionsCalculation(coroutineScope: CoroutineScope) {
        resumeActionsCalculation()
        actionCalculationJob?.cancel()
        actionCalculationJob = coroutineScope.launch(Dispatchers.Default + ceh) {
            while (isActive) {
                if (isActionCountingActive) {
                    calculateActions()
                    //log("calculateActions ended ${actionCalculationJob?.isActive} $isActive\n")
                }
                delay(ACTION_CHECK_TICK_TIME)
                //log("delay(ACTION_CHECK_TICK_TIME)\n")
            }
        }
    }

    private fun pauseActionsCalculation() {
        //log("pauseActionsCalculation")
        isActionCountingActive = false
    }

    private fun resumeActionsCalculation() {
        //log("resumeActionsCalculation")
        isActionCountingActive = true
    }

    fun stopActionsCalculation() {
        //log("stopActionCalculation")
        actionCalculationJob?.cancel()
    }

    private fun calculateActions() {
        heroStateStore.updateState { heroState ->
            when {
                heroState.nextCalcTime == 0L ||
                        heroState.isLoading ||
                        heroState.actions.firstOrNull() is Action.ActualStateAction -> {
                    //log("1_heroState")
                    pauseActionsCalculation()
                    heroState
                }

                heroState.actions.isEmpty() || heroState.actions.firstOrNull() is Action.ActualStateAction -> {
                    //log("2_$heroState")
                    pauseActionsCalculation()
                    resetPolling()
                    heroState
                }

                Calendar.getInstance().timeInMillis > heroState.nextCalcTime -> {
                    //log("3_$heroState")
                    heroState.calculateActionsRecursiveAndGet()
                }

                Calendar.getInstance().timeInMillis <= heroState.nextCalcTime -> {
                    //log("4_$heroState")
                    heroState
                }

                else -> {
                    //log("5_$heroState")
                    heroState
                }
            }
        }
    }

    private fun HeroState.calculateActionsRecursiveAndGet(): HeroState {
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

        log("Cur Action: $action")
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

                is Action.NextHallAction -> {
                    pauseActionsCalculation()
                    resetPolling()
                }

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
            lastExecutedAction = action,
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

    //-----------------------------------------------------------------------------------------------------------------
    companion object {
        const val POLLING_INTERVAL = 15000L
        const val POLLING_RETRY_DELAY = 5000L
    }

    enum class DelayOption {
        NORMAL, RETRY, DELAY
    }
}
