package ru.lemonapes.dungler.navigation.domain_models

interface DomainCraftItem {
    val gearId: GearId
    val image: Int
    val gearType: GearType
    val stats: Map<String, Int>
    val reagents: Map<ReagentId, Int>
}