package ru.lemonapes.dungler.domain_models.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable

@Suppress("ConvertObjectToDataObject")
@Immutable
@Serializable
sealed interface Action {
    @Serializable
    @Immutable
    @SerialName("ACTUAL_STATE")
    object ActualStateAction : Action

    @Serializable
    @Immutable
    @SerialName("HERO_IS_DEAD")
    object HeroIsDeadAction : Action

    @Serializable
    @Immutable
    @SerialName("NEXT_HALL")
    object NextHallAction : Action

    @Serializable
    @Immutable
    @SerialName("TAKE_LOOT")
    object TakeLootAction : Action

    sealed interface ExperienceAction : Action {
        val newExperience: Int?
        val newTotalExperience: Int?
        val newLevel: Int?
    }

    sealed interface HeroSingleDamageAction : Action {
        val damageData: HeroTargetAttackData
    }

    sealed interface HeroMassiveDamageAction : Action {
        val damageDataList: List<HeroTargetAttackData>
    }
}