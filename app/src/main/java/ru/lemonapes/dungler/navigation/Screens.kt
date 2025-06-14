package ru.lemonapes.dungler.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(open val label: String) {

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
sealed class RootScreens : Screens("") {
    @Serializable
    data object CharacterRoot : RootScreens() {
        override val label: String = "CharacterRoot"
    }

    @Serializable
    data object InventoryRoot : RootScreens() {
        override val label: String = "InventoryRoot"
    }

    @Serializable
    data object CraftRoot : RootScreens() {
        override val label: String = "CraftRoot"
    }

    @Serializable
    data object DungeonListRoot : RootScreens() {
        override val label: String = "DungeonListRoot"
    }

    @Serializable
    data object DungeonRoot : RootScreens() {
        override val label: String = "DungeonRoot"
    }
}