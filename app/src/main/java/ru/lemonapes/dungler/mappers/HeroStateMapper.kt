package ru.lemonapes.dungler.mappers

import android.icu.util.Calendar
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.hero_state.Action
import ru.lemonapes.dungler.hero_state.DungeonState
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.hero_state.HeroState.Companion.ACTION_TICK_TIME
import ru.lemonapes.dungler.network.models.ActionType
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
            isEating = response.actions.firstOrNull()?.type == ActionType.EATING_EFFECT,
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
            actions = response.actions.map {
                when (it.type) {
                    ActionType.HEAL_EFFECT ->
                        Action.HomeHealAction(healAmount = it.healEffectData?.healAmount ?: 0)

                    ActionType.HERO_ATTACK ->
                        when {
                            it.heroAttackData?.heroCommonAttackData != null -> Action.HeroAttackAction.Common(
                                damageData = HeroDamageDataMapper(it.heroAttackData.heroCommonAttackData)
                            )

                            else -> Action.HeroAttackAction.ModifierSwipingStrikes(
                                damageDataList = it.heroAttackData?.modifierSwipingStrikesData!!
                                    .map(HeroDamageDataMapper)
                                    .toPersistentList()
                            )
                        }

                    ActionType.ENEMY_ATTACK ->
                        Action.EnemyAttackAction(
                            enemyIndex = it.enemyAttackData!!.enemyIndex,
                            pureDamage = it.enemyAttackData.pureDamage,
                            attackResult = it.enemyAttackData.attackResult
                        )

                    ActionType.EATING_EFFECT ->
                        Action.EatingEffectAction(
                            healAmount = it.eatingEffectData!!.healAmount,
                            reduceFood = it.eatingEffectData.reduceFood
                        )

                    ActionType.NEXT_HALL -> Action.NextHallAction
                    ActionType.TAKE_LOOT -> Action.TakeLootAction
                    ActionType.HERO_IS_DEAD -> Action.HeroIsDeadAction
                    ActionType.ACTUAL_STATE -> Action.ActualStateAction
                    ActionType.SKILL_ACTION ->
                        when (it.skillDataDto!!.skillId) {
                            SkillId.HEROIC_STRIKE -> Action.SkillAction.HeroicStrike(
                                damageData = HeroDamageDataMapper(it.skillDataDto.dataHeroicStrike!!)
                            )

                            SkillId.SWIPING_STRIKES -> Action.SkillAction.SwipingStrikes(
                                damageDataList = it.skillDataDto.dataSwipingStrikes!!
                                    .map(HeroDamageDataMapper)
                                    .toPersistentList()
                            )

                            SkillId.WHIRLWIND -> Action.SkillAction.Whirlwind(
                                damageDataList = it.skillDataDto.dataSwipingStrikes!!
                                    .map(HeroDamageDataMapper)
                                    .toPersistentList()
                            )
                        }
                }
            }.toPersistentList(),
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
