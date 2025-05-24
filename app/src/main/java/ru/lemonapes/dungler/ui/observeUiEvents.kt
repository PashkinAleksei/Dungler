package ru.lemonapes.dungler.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.State
import ru.lemonapes.dungler.parent_view_model.UiEvent

@SuppressLint("ComposableNaming")
@Composable
fun <T : State> ParentViewModel<T>.observeUiEvents(navController: NavController) {
    LaunchedEffect(this) {
        uiEvents.collect { event ->
            when (event) {
                is UiEvent.NavigateBack -> navController.popBackStack()
                is UiEvent.NavigateTo -> navController.navigate(event.route)
            }
        }
    }
}