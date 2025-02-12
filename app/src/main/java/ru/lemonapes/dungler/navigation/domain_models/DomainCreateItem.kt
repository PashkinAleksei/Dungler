package ru.lemonapes.dungler.navigation.domain_models

import androidx.annotation.DrawableRes
import ru.lemonapes.dungler.R

data class DomainCreateItem(
    override val gearId: GearId,
    @DrawableRes override val image: Int,
    override val gearType: GearType,
    override val stats: Map<String, Int>,
    override val reagents: Map<ReagentId, Int>,
) : DomainCraftItem {
    companion object {
        fun getMock() = DomainCreateItem(
            gearId = GearId.GREEN_KNIGHT_GLOVES,
            image = R.drawable.green_knight_gloves_10,
            gearType = GearType.GLOVES,
            stats = mapOf("Strength" to 2),
            reagents = mapOf(
                ReagentId.LINEN_CLOTH to 10
            )
        )
    }
}