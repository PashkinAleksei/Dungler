package ru.lemonapes.dungler.ui

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

class Utils {
    companion object {
        @Composable
        fun getFadeAlpha(key: Any?): Float {
            if (key == null) return 0f
            var alpha by remember { mutableFloatStateOf(1f) }
            LaunchedEffect(key) {
                alpha = 1f
                delay(500)

                animate(
                    initialValue = 1f,
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 500)
                ) { value, _ ->
                    alpha = value
                }
            }
            return alpha
        }
    }
}