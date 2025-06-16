package ru.lemonapes.dungler.hero_state

import kotlinx.collections.immutable.ImmutableList
import ru.lemonapes.dungler.domain_models.SkillId

sealed interface Action {
    data class HealAction(
        val healAmount: Int,
    ) : Action

    sealed interface HeroAttackAction : Action {
        val damageData: Any

        data class Common(
            override val damageData: HeroDamageData,
        ) : HeroAttackAction

        data class ModifierSwipingStrikes(
            override val damageData: ImmutableList<HeroDamageData>,
        ) : HeroAttackAction
    }

    data class EnemyAttackAction(
        val enemyIndex: Int,
        val enemyPureDamage: Int,
    ) : Action

    data class EatingEffectAction(
        val healAmount: Int,
        val reduceFood: Boolean,
    ) : Action

    data object NextHallAction : Action
    data object TakeLootAction : Action
    data object HeroIsDeadAction : Action
    data object ActualStateAction : Action

    sealed interface SkillAction : Action {
        val skillId: SkillId
        val damageData: Any

        data class HeroicStrike(
            override val damageData: HeroDamageData,
        ) : SkillAction {
            override val skillId = SkillId.HEROIC_STRIKE
        }

        data class SwipingStrikes(
            override val damageData: ImmutableList<HeroDamageData>,
        ) : SkillAction {
            override val skillId = SkillId.SWIPING_STRIKES
        }

        data class Whirlwind(
            override val damageData: ImmutableList<HeroDamageData>,
        ) : SkillAction {
            override val skillId = SkillId.WHIRLWIND
        }
    }
}

