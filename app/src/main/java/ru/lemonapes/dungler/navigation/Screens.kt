package ru.lemonapes.dungler.navigation

import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Serializable
sealed class Screens(val label: String) {
    @Serializable
    data object Character : Screens("Character")
    @Serializable
    data object Inventory : Screens("Inventory")
    @Serializable
    data object Craft : Screens("Craft")
    @Serializable
    data object Dungeons : Screens("Dungeons")
}