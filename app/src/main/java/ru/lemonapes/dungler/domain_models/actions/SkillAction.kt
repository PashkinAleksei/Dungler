package ru.lemonapes.dungler.domain_models.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.SkillId
import javax.annotation.concurrent.Immutable

@Immutable
sealed interface SkillAction : Action.ExperienceAction {
    val skillId: SkillId
    val cooldown: Int

    @Serializable
    @Immutable
    @SerialName("SKILL_HEROIC_STRIKE_ACTION")
    class HeroicStrike(
        override val damageData: HeroTargetAttackData,
        override val newExperience: Int?,
        override val newTotalExperience: Int?,
        override val newLevel: Int? = null,
        override val cooldown: Int,
    ) : SkillAction, Action.HeroSingleDamageAction {
        override val skillId = SkillId.HEROIC_STRIKE
    }

    @Serializable
    @Immutable
    @SerialName("SKILL_SWIPING_STRIKES_ACTION")
    class SwipingStrikes(
        override val damageDataList: List<HeroTargetAttackData>,
        override val newExperience: Int?,
        override val newTotalExperience: Int?,
        override val newLevel: Int? = null,
        override val cooldown: Int,
    ) : SkillAction, Action.HeroMassiveDamageAction {
        override val skillId = SkillId.SWIPING_STRIKES
    }

    @Serializable
    @Immutable
    @SerialName("SKILL_WHIRLWIND_ACTION")
    class Whirlwind(
        override val damageDataList: List<HeroTargetAttackData>,
        override val newExperience: Int?,
        override val newTotalExperience: Int?,
        override val newLevel: Int? = null,
        override val cooldown: Int,
    ) : SkillAction, Action.HeroMassiveDamageAction {
        override val skillId = SkillId.WHIRLWIND
    }
}