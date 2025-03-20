package ru.lemonapes.dungler.ui.theme.typographies

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@JvmInline
value class AppTextStyle(val textStyle: TextStyle)

/**
 * A composition local for [LimonApesTypographies].
 */
internal val LocalThemeTypographies = staticCompositionLocalOf { LimonApesTypographies() }

/**
 * LimonApes typographies
 */
@Immutable
data class LimonApesTypographies(
    val bold36: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val bold24: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val semibold24: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W500,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regular24: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val bold20: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 20.sp,
            lineHeight = 30.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regular20: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 20.sp,
            lineHeight = 30.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regular18: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val bold16: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regular16: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val boldUnderline16: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            textDecoration = TextDecoration.Underline,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val boldItalic16: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontStyle = FontStyle.Italic,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regularItalic16: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontStyle = FontStyle.Italic,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val bold14: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regular14: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regular14Compact: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 14.sp,
            lineHeight = 12.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val boldUnderline14: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            textDecoration = TextDecoration.Underline,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regularUnderline14: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            textDecoration = TextDecoration.Underline,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val bold12: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regular12: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val bold10: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 10.sp,
            lineHeight = 12.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regular10: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 10.sp,
            lineHeight = 12.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
    val regular6: AppTextStyle = AppTextStyle(
        TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = fontsMuseoSansCyrl,
            fontSize = 6.sp,
            lineHeight = 7.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        )
    ),
)
