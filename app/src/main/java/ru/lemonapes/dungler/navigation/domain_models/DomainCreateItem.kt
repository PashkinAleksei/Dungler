package ru.lemonapes.dungler.navigation.domain_models

import ru.lemonapes.dungler.R

data class DomainCreateItem(
    override val gearId: GearId,
    override val gearData: GearData,
    override val gearType: GearType,
    override val stats: Map<String, Int>,
    override val reagents: Map<String, Int>,
):DomainCraftItem {
    companion object {
        fun getMock() = DomainCreateItem(
            gearId = GearId.GREEN_KNIGHT_GLOVES,
            gearData = GearData(
                name = R.string.green_knight_gloves_name,
                image = R.drawable.green_knight_gloves_10
            ),
            gearType = GearType.GLOVES,
            stats = mapOf("Strength" to 2),
            reagents = mapOf("copper" to 10)
        )
    }
}