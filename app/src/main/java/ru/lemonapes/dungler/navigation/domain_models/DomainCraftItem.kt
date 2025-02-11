package ru.lemonapes.dungler.navigation.domain_models

import ru.lemonapes.dungler.R

data class DomainCraftItem(
    val gearId: GearId,
    val gearData: GearData,
    val gearType: GearType,
    val stats: Map<String, Int>,
    val reagents: Map<String, Int>,
) {
    companion object {
        fun getMock() = DomainCraftItem(
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