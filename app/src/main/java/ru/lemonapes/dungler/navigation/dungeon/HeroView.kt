package ru.lemonapes.dungler.navigation.dungeon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.Utils
import ru.lemonapes.dungler.ui.models.getLastHeroHpChange
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun HeroState.HeroView(
    modifier: Modifier = Modifier,
) {
    Card(modifier, shape = RoundedCornerShape(12.dp)) {
        Box(
            modifier = Modifier
                .padding(3.dp)
                .aspectRatio(0.75f),
        ) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                Image(
                    painter = painterResource(R.drawable.img_hero),
                    contentDescription = stringResource(R.string.hero),
                    contentScale = ContentScale.Crop,
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Column {
                    Row(
                        Modifier
                            .padding(top = 2.dp)
                            .padding(horizontal = 6.dp)
                    ) {
                        LevelSurface()
                        Column(Modifier.height(32.dp)) {
                            Spacer(Modifier.weight(1f))
                            HealthBar()
                            ExperienceBar()
                            Spacer(Modifier.weight(1f))
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                }

                lastExecutedAction?.getLastHeroHpChange()?.let { hpChangeVO ->
                    val fadeAlpha = Utils.getFadeAlpha(lastExecutedAction)
                    DamageImageView(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .graphicsLayer { this.alpha = fadeAlpha },
                        hpChangeVO = hpChangeVO,
                        textStyle = LocalThemeTypographies.current.bold24
                    )
                }

            }
        }
    }
}

@Composable
private fun HeroState.LevelSurface() {
    val levelText = if (isLoading) {
        null
    } else {
        level?.toString()
    } ?: "??"
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(LocalThemeColors.current.surfaceColor),
        contentAlignment = Alignment.Center
    ) {
        val padding = if (levelText.length < 10) 10.dp else 4.dp
        UIText(
            Modifier.padding(horizontal = padding),
            textAlign = TextAlign.Center,
            text = levelText,
            color = LocalThemeColors.current.barsTextColor,
            textStyle = LocalThemeTypographies.current.bold16
        )
    }
}

@Composable
private fun HeroState.HealthBar(
    modifier: Modifier = Modifier,
) {
    health?.let {
        totalHealth?.let {
            val healthProgress = health.toFloat().div(totalHealth.toFloat())
            val healthText = "$health/$totalHealth"

            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .height(16.dp),
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
            ) {
                LinearProgressIndicator(
                    progress = { healthProgress },
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
                    textStyle = LocalThemeTypographies.current.regular14
                )
            }
        }
    }
}

@Composable
private fun HeroState.ExperienceBar(
    modifier: Modifier = Modifier,
) {
    experience?.let {
        totalExperience?.let {
            val healthProgress = experience.toFloat().div(totalExperience.toFloat())
            val healthText = "$experience/$totalExperience"

            Surface(
                modifier = modifier
                    .padding(top = 2.dp)
                    .fillMaxWidth()
                    .height(12.dp),
                shape = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp)
            ) {
                LinearProgressIndicator(
                    progress = { healthProgress },
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
                    text = healthText,
                    color = LocalThemeColors.current.barsTextColor,
                    textStyle = LocalThemeTypographies.current.regular10
                )
            }
        }
    }
}

@Preview
@Composable
fun HeroViewPreview() {
    DunglerTheme(darkTheme = true) {
        HeroState.MOCK.HeroView()
    }
} 