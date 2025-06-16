package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.hero_state.HeroDamageData
import ru.lemonapes.dungler.network.models.actions.action_data.HeroDamageDataDto

object HeroDamageDataMapper : (HeroDamageDataDto) -> HeroDamageData {
    override fun invoke(heroDamageData: HeroDamageDataDto): HeroDamageData {
        return HeroDamageData(
            heroPureDamage = heroDamageData.heroPureDamage,
            targetIndex = heroDamageData.targetIndex,
        )
    }
}
