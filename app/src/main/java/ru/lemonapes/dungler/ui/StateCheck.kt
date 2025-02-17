package ru.lemonapes.dungler.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.lemonapes.dungler.parent_store.State

@Composable
fun StateCheck(
    modifier: Modifier = Modifier,
    state: State,
    listener: StateListener,
    content: @Composable () -> Unit,
) {
    when {
        state.isLoading -> {
            LoaderUi(modifier)
        }

        state.error != null -> {
            ErrorUi(modifier, listener)
        }

        else -> {
            content()
        }
    }
}

interface StateListener : ErrorListener