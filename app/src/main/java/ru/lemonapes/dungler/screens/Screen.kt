package ru.lemonapes.dungler.screens

import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Serializable
sealed class Screen(val route: String, val icon: Int, val label: String) {
    @Serializable
    data object Character : Screen("character", R.drawable.ic_helm, "Character")
    @Serializable
    data object Inventory : Screen("inventory", R.drawable.ic_backpack, "Inventory")
    @Serializable
    data object Craft : Screen("craft", R.drawable.ic_blacksmith, "Craft")
    @Serializable
    data object Dungeons : Screen("dungeons", R.drawable.ic_cave, "Dungeons")
}