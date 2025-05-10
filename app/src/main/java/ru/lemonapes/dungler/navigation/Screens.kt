package ru.lemonapes.dungler.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(val label: String) {
    @Serializable
    data object CharacterRoot : Screens("CharacterRoot")

    @Serializable
    data object Equipment : Screens("Equipment")

    @Serializable
    data object Spells : Screens("Spells")

    @Serializable
    data object InventoryRoot : Screens("InventoryRoot")

    @Serializable
    data object Inventory : Screens("Inventory")

    @Serializable
    data object CraftRoot : Screens("CraftRoot")
    @Serializable
    data object Craft : Screens("Craft")

    @Serializable
    data object DungeonListRoot : Screens("DungeonListRoot")

    @Serializable
    data object DungeonList : Screens("DungeonList")

    @Serializable
    data object DungeonRoot : Screens("DungeonRoot")

    @Serializable
    data object Dungeon : Screens("Dungeon")
}