package ru.lemonapes.dungler.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(open val label: String) {

    @Serializable
    data object Equipment : Screens("Equipment")

    @Serializable
    data object EquipmentSkills : Screens("EquipmentSkills")

    @Serializable
    data object SkillList : Screens("SkillList")

    @Serializable
    data object Inventory : Screens("Inventory")
    @Serializable
    data object Craft : Screens("Craft")

    @Serializable
    data object DungeonList : Screens("DungeonList")

    @Serializable
    data object Dungeon : Screens("Dungeon")
}

@Serializable
sealed class RootScreens(open val label: String) {
    @Serializable
    data object CharacterRoot : RootScreens("CharacterRoot")

    @Serializable
    data object InventoryRoot : RootScreens("InventoryRoot")

    @Serializable
    data object CraftRoot : RootScreens("CraftRoot")

    @Serializable
    data object DungeonListRoot : RootScreens("DungeonListRoot")

    @Serializable
    data object DungeonRoot : RootScreens("DungeonRoot")
}