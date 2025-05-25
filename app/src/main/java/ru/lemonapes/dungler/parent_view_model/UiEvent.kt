package ru.lemonapes.dungler.parent_view_model

import ru.lemonapes.dungler.navigation.Screens

sealed class UiEvent {
    data object NavigateBack : UiEvent()
    data class NavigateTo(val route: Screens) : UiEvent()
}
