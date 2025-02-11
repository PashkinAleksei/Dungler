package ru.lemonapes.dungler.navigation.domain_models

interface DomainCraftItem {
    val gearId: GearId
    val gearData: GearData
    val gearType: GearType
    val stats: Map<String, Int>
    val reagents: Map<String, Int>
}