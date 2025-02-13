package ru.lemonapes.dungler.domain_models

import androidx.annotation.DrawableRes
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.R

data class DomainUpgradeItem(
    override val gearId: GearId,
    @DrawableRes override val image: Int,
    override val gearType: GearType,
    override val stats: ImmutableMap<String, Int>,
    override val reagents: ImmutableMap<ReagentId, Int>,
    val level: Int,
    val nextStats: ImmutableMap<String, Int>,
) : DomainCraftItem {
    companion object {
        fun getMock() = DomainUpgradeItem(
            gearId = GearId.GREEN_KNIGHT_HELM,
            image = R.drawable.green_knight_helm,
            gearType = GearType.HELM,
            level = 5,
            stats = persistentMapOf(
                "armor" to 13,
                "intelligence" to 11,
                "min_hero_level" to 8,
                "stamina" to 8
            ),
            nextStats = persistentMapOf(
                "armor" to 16,
                "intelligence" to 14,
                "min_hero_level" to 10,
                "stamina" to 11
            ),
            reagents = persistentMapOf(
                ReagentId.COPPER to 41,
                ReagentId.LINEN_CLOTH to 51,
                ReagentId.TOPAZ to 3
            )
        )
    }
}