package ru.lemonapes.dungler.navigation.domain_models

import ru.lemonapes.dungler.R

data class DomainUpgradeItem(
    override val gearId: GearId,
    override val gearData: GearData,
    override val gearType: GearType,
    override val stats: Map<String, Int>,
    override val reagents: Map<String, Int>,
    val level: Int,
    val nextStats: Map<String, Int>,
) : DomainCraftItem {
    companion object {
        fun getMock() = DomainUpgradeItem(
            gearId = GearId.GREEN_KNIGHT_HELM, // пример, замените на нужное значение
            gearData = GearData(
                name = R.string.green_knight_helm_name,
                image = R.drawable.green_knight_helm
            ),
            gearType = GearType.HELM, // пример, замените на нужное значение
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
                "copper" to 41,
                "linen_cloth" to 51,
                "topaz" to 3
            )
        )
    }
}