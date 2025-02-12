package ru.lemonapes.dungler.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.AppTextStyle


@Composable
fun UIText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: AppTextStyle,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    color: Color = LocalThemeColors.current.primaryTextColor,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    textDecoration: TextDecoration? = null,
) {
    Text(
        text = text,
        textAlign = textAlign,
        modifier = modifier,
        color = color,
        maxLines = maxLines,
        style = textStyle.textStyle,
        overflow = overflow,
        softWrap = softWrap,
        textDecoration = textDecoration,
    )
}

@Composable
fun UIText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textStyle: AppTextStyle,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    color: Color = LocalThemeColors.current.primaryTextColor,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    textDecoration: TextDecoration? = null,
) {
    Text(
        text = text,
        textAlign = textAlign,
        modifier = modifier,
        color = color,
        maxLines = maxLines,
        style = textStyle.textStyle,
        overflow = overflow,
        softWrap = softWrap,
        textDecoration = textDecoration,
    )
}
