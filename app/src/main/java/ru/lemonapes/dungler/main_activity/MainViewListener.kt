package ru.lemonapes.dungler.main_activity

class MainViewListener(
    val onDungeonExitClick: () -> Unit,
) {
    companion object {
        val EMPTY
            get() = MainViewListener(
                onDungeonExitClick = {}
            )
    }
} 