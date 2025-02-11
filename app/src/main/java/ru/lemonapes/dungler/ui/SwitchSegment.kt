package ru.lemonapes.dungler.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Enum для управления выбором
enum class SwitchSegment {
    LEFT, RIGHT
}

/**
 * Сегментированный переключатель, разделённый на две горизонтальные части.
 *
 * @param leftText Текст левой части.
 * @param rightText Текст правой части.
 * @param selectedSegment Текущий выбранный сегмент.
 * @param onSelectionChanged Callback при смене выбранного сегмента.
 * @param modifier Модификатор для контейнера.
 * @param selectedElevation Elevation для выбранного сегмента.
 * @param unselectedElevation Elevation для невыбранного сегмента.
 */
@Composable
fun SegmentedSwitch(
    leftText: String,
    rightText: String,
    selectedSegment: SwitchSegment,
    onSelectionChanged: (SwitchSegment) -> Unit,
    modifier: Modifier = Modifier,
    selectedElevation: Dp = 0.dp,
    unselectedElevation: Dp = 8.dp,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50)) // Общая форма переключателя
            .height(IntrinsicSize.Min)
    ) {
        // Левая часть
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onSelectionChanged(SwitchSegment.LEFT) },
            color = if (selectedSegment == SwitchSegment.LEFT) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            shadowElevation = if (selectedSegment == SwitchSegment.LEFT) selectedElevation else unselectedElevation,
            shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = leftText,
                    color = if (selectedSegment == SwitchSegment.LEFT) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
        // Правая часть
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onSelectionChanged(SwitchSegment.RIGHT) },
            color = if (selectedSegment == SwitchSegment.RIGHT) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            shadowElevation = if (selectedSegment == SwitchSegment.RIGHT) selectedElevation else unselectedElevation,
            shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = rightText,
                    color = if (selectedSegment == SwitchSegment.RIGHT) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
