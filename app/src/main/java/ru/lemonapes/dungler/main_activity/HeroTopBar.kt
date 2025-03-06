package ru.lemonapes.dungler.main_activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun HeroTopBar(
    modifier: Modifier = Modifier,
    heroState: HeroState,
) {
    val levelText = if (heroState.isLoading) {
        null
    } else {
        heroState.level?.toString()
    } ?: "??"

    Row(
        modifier = modifier.padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LocalThemeColors.current.surfaceColor),
            contentAlignment = Alignment.Center
        ) {
            UIText(
                textAlign = TextAlign.Center,
                text = levelText,
                color = LocalThemeColors.current.barsTextColor,
                textStyle = LocalThemeTypographies.current.bold24
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            HealthBar(heroState = heroState)
            Spacer(modifier = Modifier.height(1.dp))
            ExperienceBar(heroState = heroState)
        }
    }
}

@Composable
private fun HealthBar(
    modifier: Modifier = Modifier,
    heroState: HeroState,
) {
    val healthProgress = if (heroState.isLoading) {
        0f
    } else {
        heroState.health?.let { hp ->
            heroState.totalHealth?.let { tHp ->
                hp.toFloat().div(tHp.toFloat())
            }
        }
    }
    val healthText = if (heroState.isLoading) {
        null
    } else {
        heroState.health?.let { hp ->
            heroState.totalHealth?.let { tHp ->
                "$hp/$tHp"
            }
        }
    } ?: "??/??"

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
    ) {
        LinearProgressIndicator(
            progress = { healthProgress ?: 0f },
            modifier = Modifier
                .fillMaxSize(),
            color = LocalThemeColors.current.healthBarColor,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Square,
            drawStopIndicator = {}
        )
        UIText(
            modifier = Modifier
                .fillMaxSize(),
            textAlign = TextAlign.Center,
            text = healthText,
            color = LocalThemeColors.current.barsTextColor,
            textStyle = LocalThemeTypographies.current.regular16
        )
    }
}

@Composable
private fun ExperienceBar(
    modifier: Modifier = Modifier,
    heroState: HeroState,
) {
    val experienceText = heroState.experience?.let { hp ->
        heroState.totalExperience?.let { tHp ->
            "$hp/$tHp"
        }
    } ?: "??/??"

    val experienceProgress = heroState.experience?.let { exp ->
        heroState.totalExperience?.let { tExp ->
            exp.toFloat().div(tExp.toFloat())
        }
    }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp),
        shape = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp)
    ) {
        LinearProgressIndicator(
            progress = { experienceProgress ?: 0f },
            modifier = Modifier
                .fillMaxSize(),
            color = LocalThemeColors.current.experienceColor,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Square,
            drawStopIndicator = {}
        )
        UIText(
            modifier = Modifier
                .fillMaxSize(),
            textAlign = TextAlign.Center,
            text = experienceText,
            color = LocalThemeColors.current.barsTextColor,
            textStyle = LocalThemeTypographies.current.regular10
        )
    }
}

@Preview
@Composable
fun HeroTopBarPreview() {
    DunglerTheme(darkTheme = true) {
        HeroTopBar(heroState = HeroState.MOCK)
    }
}