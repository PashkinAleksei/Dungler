package ru.lemonapes.dungler.navigation.domain_models

import androidx.annotation.DrawableRes
import ru.lemonapes.dungler.R

data class DomainUpgradeItem(
    override val gearId: GearId,
    @DrawableRes override val image: Int,
    override val gearType: GearType,
    override val stats: Map<String, Int>,
    override val reagents: Map<ReagentId, Int>,
    val level: Int,
    val nextStats: Map<String, Int>,
) : DomainCraftItem {
    companion object {
        fun getMock() = DomainUpgradeItem(
            gearId = GearId.GREEN_KNIGHT_HELM,
            image = R.drawable.green_knight_helm,
            gearType = GearType.HELM,
            level = 5,
            stats = mapOf(
                "armor" to 13,
                "intelligence" to 11,
                "min_hero_level" to 8,
                "stamina" to 8
            ),
            nextStats = mapOf(
                "armor" to 16,
                "intelligence" to 14,
                "min_hero_level" to 10,
                "stamina" to 11
            ),
            reagents = mapOf(
                ReagentId.COPPER to 41,
                ReagentId.LINEN_CLOTH to 51,
                ReagentId.TOPAZ to 3
            )
        )
    }
}