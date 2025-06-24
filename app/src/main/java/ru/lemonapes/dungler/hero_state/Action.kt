package ru.lemonapes.dungler.hero_state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import ru.lemonapes.dungler.domain_models.AttackResult
import ru.lemonapes.dungler.domain_models.SkillId

@Suppress("ConvertObjectToDataObject")
@Immutable
sealed interface Action {
    @Immutable
    sealed interface HomeAction : Action

    @Immutable
    class HomeHealAction(
        val healAmount: Int,
    ) : HomeAction

    @Immutable
    sealed interface HeroAttackAction : Action {
        val damageData: Any

        @Immutable
        class Common(
            override val damageData: HeroDamageData,
        ) : HeroAttackAction, SingleDamage

        @Immutable
        class ModifierSwipingStrikes(
            override val damageData: ImmutableList<HeroDamageData>,
        ) : HeroAttackAction, MassiveDamage

        companion object {
            val MOCK get() = Common(HeroDamageData.MOCK)
        }
    }

    @Immutable
    class EnemyAttackAction(
        val enemyIndex: Int,
        val pureDamage: Int,
        val attackResult: AttackResult,
    ) : Action

    @Immutable
    class EatingEffectAction(
        val healAmount: Int,
        val reduceFood: Boolean,
    ) : Action

    @Immutable
    object NextHallAction : Action
    object TakeLootAction : Action
    object HeroIsDeadAction : HomeAction
    object ActualStateAction : HomeAction

    @Immutable
    sealed interface SkillAction : Action {
        val skillId: SkillId
        val damageData: Any

        @Immutable
        class HeroicStrike(
            override val damageData: HeroDamageData,
        ) : SkillAction, SingleDamage {
            override val skillId = SkillId.HEROIC_STRIKE
        }

        @Immutable
        class SwipingStrikes(
            override val damageData: ImmutableList<HeroDamageData>,
        ) : SkillAction, MassiveDamage {
            override val skillId = SkillId.SWIPING_STRIKES
        }

        @Immutable
        class Whirlwind(
            override val damageData: ImmutableList<HeroDamageData>,
        ) : SkillAction, MassiveDamage {
            override val skillId = SkillId.WHIRLWIND
        }
    }

    @Immutable
    sealed interface SingleDamage : Action {
        val damageData: HeroDamageData
    }

    @Immutable
    sealed interface MassiveDamage : Action {
        val damageData: ImmutableList<HeroDamageData>
    }
}

