package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.hero_state.HeroDamageData
import ru.lemonapes.dungler.network.models.actions.action_data.HeroTargetAttackDto

object HeroDamageDataMapper : (HeroTargetAttackDto) -> HeroDamageData {
    override fun invoke(heroDamageData: HeroTargetAttackDto): HeroDamageData {
        return HeroDamageData(
            heroPureDamage = heroDamageData.heroPureDamage,
            targetIndex = heroDamageData.targetIndex,
            attackResult = heroDamageData.attackResult,
        )
    }
}
