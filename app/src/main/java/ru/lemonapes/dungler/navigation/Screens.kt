package ru.lemonapes.dungler.navigation

import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.navigation.character.SkillSlot

@Serializable
sealed class Screens(val label: String) {

    @Serializable
    data object Equipment : Screens("Equipment")

    @Serializable
    data object EquipmentSkills : Screens("EquipmentSkills")

    @Serializable
    data class SkillList(val skillSlot: SkillSlot) : Screens("SkillList")

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
sealed class RootScreens(val label: String) {
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