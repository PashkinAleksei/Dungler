package ru.lemonapes.dungler.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun LoaderUi(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = LocalThemeColors.current.primaryTextColor,
            modifier = Modifier.align(alignment = Alignment.Center),
        )
    }
}