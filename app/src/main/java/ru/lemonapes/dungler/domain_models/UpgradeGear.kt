package ru.lemonapes.dungler.domain_models

import androidx.annotation.DrawableRes
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.R

data class UpgradeGear(
    override val gearId: GearId,
    @DrawableRes override val image: Int,
    override val stats: ImmutableMap<StatId, Int>,
    override val reagents: ImmutableMap<ReagentId, Int>,
    val level: Int,
    val nextStats: ImmutableMap<StatId, Int>,
) : CraftGear {
    companion object {
        fun getMock() = UpgradeGear(
            gearId = GearId.GREEN_KNIGHT_HELM,
            image = R.drawable.green_knight_helm,
            level = 5,
            stats = persistentMapOf(
                StatId.ARMOR to 13,
                StatId.INTELLECT to 11,
                StatId.MIN_HERO_LEVEL to 8,
                StatId.STAMINA to 8
            ),
            nextStats = persistentMapOf(
                StatId.ARMOR to 16,
                StatId.INTELLECT to 14,
                StatId.MIN_HERO_LEVEL to 10,
                StatId.STAMINA to 11
            ),
            reagents = persistentMapOf(
                ReagentId.COPPER to 41,
                ReagentId.LINEN_CLOTH to 51,
                ReagentId.TOPAZ to 3
            )
        )
    }
}