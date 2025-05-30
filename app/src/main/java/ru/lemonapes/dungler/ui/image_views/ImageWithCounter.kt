package ru.lemonapes.dungler.ui.image_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun ImageWithCounter(
    modifier: Modifier = Modifier,
    painter: Painter,
    counter: Int?,
    contentDescription: String,
    imagePadding: PaddingValues = PaddingValues(),
) {
    val textHorizontalPadding = 2

    val textStyle = LocalThemeTypographies.current.regular20
    val textPaddingGap = 3
    val surfaceMinWidth = textStyle.textStyle.fontSize.value +
            textHorizontalPadding * 2 +
            textPaddingGap

    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(imagePadding),
            painter = painter,
            contentDescription = contentDescription,
        )
        counter?.let {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(2.dp)
                    .defaultMinSize(minWidth = surfaceMinWidth.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                UIText(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = textHorizontalPadding.dp)
                        .padding(vertical = 2.dp),
                    text = "$counter",
                    color = LocalThemeColors.current.primaryTextColor,
                    textStyle = textStyle,
                )
            }
        }
    }
}