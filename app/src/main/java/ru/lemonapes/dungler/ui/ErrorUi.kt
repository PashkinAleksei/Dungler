package ru.lemonapes.dungler.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun ErrorUi(modifier: Modifier = Modifier, listener: ErrorListener) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                UIText(
                    modifier = Modifier.align(Alignment.Center),
                    textStyle = LocalThemeTypographies.current.regular20,
                    text = stringResource(R.string.error_text)
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Center),
                    onClick = listener.onRetryClick
                ) {
                    UIText(
                        textStyle = LocalThemeTypographies.current.regular20,
                        text = stringResource(R.string.error_button_text)
                    )
                }
            }
        }
    }
}

interface ErrorListener {
    val onRetryClick: () -> Unit

    companion object {
        val EMPTY
            get() = object : ErrorListener {
                override val onRetryClick = {}
            }
    }
}

@Preview
@Composable
private fun ErrorUiPreview() {
    DunglerTheme(darkTheme = true) {
        ErrorUi(listener = ErrorListener.EMPTY)
    }
}