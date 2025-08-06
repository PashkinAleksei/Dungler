package ru.lemonapes.dungler.domain_models.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable

@Serializable
@Immutable
sealed interface HeroAttackAction : Action.ExperienceAction {
    @Serializable
    @Immutable
    @SerialName("HERO_COMMON_ATTACK")
    class Common(
        override val damageData: HeroTargetAttackData,
        override val newExperience: Int? = null,
        override val newTotalExperience: Int? = null,
        override val newLevel: Int? = null,
    ) : HeroAttackAction, Action.HeroSingleDamageAction

    @Serializable
    @Immutable
    @SerialName("HERO_MODIFIER_SWIPING_STRIKES_ATTACK")
    class ModifierSwipingStrikes(
        override val damageDataList: List<HeroTargetAttackData>,
        override val newExperience: Int? = null,
        override val newTotalExperience: Int? = null,
        override val newLevel: Int? = null,
    ) : HeroAttackAction, Action.HeroMassiveDamageAction

    companion object {
        val MOCK get() = Common(HeroTargetAttackData.MOCK)
    }
}