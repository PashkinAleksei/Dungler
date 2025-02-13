package ru.lemonapes.dungler.domain_models

import androidx.annotation.DrawableRes
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.R

data class DomainCreateItem(
    override val gearId: GearId,
    @DrawableRes override val image: Int,
    override val gearType: GearType,
    override val stats: ImmutableMap<String, Int>,
    override val reagents: ImmutableMap<ReagentId, Int>,
) : DomainCraftItem {
    companion object {
        fun getMock() = DomainCreateItem(
            gearId = GearId.GREEN_KNIGHT_GLOVES,
            image = R.drawable.green_knight_gloves_10,
            gearType = GearType.GLOVES,
            stats = persistentMapOf("Strength" to 2),
            reagents = persistentMapOf(
                ReagentId.LINEN_CLOTH to 10
            )
        )
    }
}