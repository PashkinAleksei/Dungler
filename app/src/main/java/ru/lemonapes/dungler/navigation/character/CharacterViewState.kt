package ru.lemonapes.dungler.navigation.character

import com.limonapes.dungle.parent_store.State

data class CharacterViewState(
    val error: Throwable? = null
) : State {
    companion object {
        fun getEmpty() = CharacterViewState()
    }
}