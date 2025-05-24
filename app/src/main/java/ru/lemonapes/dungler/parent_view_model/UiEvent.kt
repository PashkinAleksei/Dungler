package ru.lemonapes.dungler.parent_view_model

sealed class UiEvent {
    data object NavigateBack : UiEvent()
    data class NavigateTo(val route: String) : UiEvent()
}
