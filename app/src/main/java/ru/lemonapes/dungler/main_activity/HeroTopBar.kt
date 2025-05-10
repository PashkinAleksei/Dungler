package ru.lemonapes.dungler.main_activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun HeroTopBar(
    modifier: Modifier = Modifier,
    topBarListener: TopBarListener,
    heroState: HeroState,
    activeButton: TopBarButtonActive?,
) {
    Row(
        //modifier = modifier.padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        heroState.HeroStateBar(modifier = Modifier.weight(1f))
        Buttons(topBarListener, activeButton)
    }
}

@Composable
private fun HeroState.HeroStateBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LevelSurface()
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            HealthBar()
            Spacer(modifier = Modifier.height(1.dp))
            ExperienceBar()
        }
    }
}

@Composable
private fun Buttons(
    topBarListener: TopBarListener,
    topBarButtonActive: TopBarButtonActive?,
) {
    when (topBarButtonActive) {
        TopBarButtonActive.EQUIPMENT, TopBarButtonActive.SPELLS -> {
            val equipmentBorderColor: Color
            val spellsBorderColor: Color
            if (topBarButtonActive == TopBarButtonActive.EQUIPMENT) {
                equipmentBorderColor = LocalThemeColors.current.primaryTextColor
                spellsBorderColor = LocalThemeColors.current.secondaryTextColor
            } else {
                equipmentBorderColor = LocalThemeColors.current.secondaryTextColor
                spellsBorderColor = LocalThemeColors.current.primaryTextColor
            }
            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .border(1.dp, equipmentBorderColor, RoundedCornerShape(12.dp))
                    .minimumInteractiveComponentSize()
                    .size(40.dp)
                    .clickable(
                        onClick = topBarListener.toEquipment,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            bounded = false,
                            radius = 25.dp
                        )
                    ),
            ) {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(R.drawable.green_knight_chest_10),
                    contentDescription = stringResource(R.string.equipment_screen)
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp)
                    .border(1.dp, spellsBorderColor, RoundedCornerShape(12.dp))
                    .minimumInteractiveComponentSize()
                    .size(40.dp)
                    .clickable(
                        onClick = topBarListener.toSpells,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            bounded = false,
                            radius = 25.dp
                        )
                    ),
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 4.dp),
                    painter = painterResource(R.drawable.beril),
                    contentDescription = stringResource(R.string.spell_equipment_screen)
                )
            }
        }

        else -> {
            Spacer(Modifier.width(102.dp))
        }
    }
}

class TopBarListener(
    val toEquipment: () -> Unit,
    val toSpells: () -> Unit,
) {
    companion object {
        val EMPTY
            get() = TopBarListener({}, {})
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
}

@Composable
private fun HeroState.HealthBar(
    modifier: Modifier = Modifier,
) {
    val healthProgress = if (isLoading) {
        0f
    } else {
        health?.let { hp ->
            totalHealth?.let { tHp ->
                hp.toFloat().div(tHp.toFloat())
            }
        }
    }
    val healthText = if (isLoading) {
        null
    } else {
        health?.let { hp ->
            totalHealth?.let { tHp ->
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
private fun HeroState.ExperienceBar(
    modifier: Modifier = Modifier,
) {
    val experienceText = experience?.let { hp ->
        totalExperience?.let { tHp ->
            "$hp/$tHp"
        }
    } ?: "??/??"

    val experienceProgress = experience?.let { exp ->
        totalExperience?.let { tExp ->
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

enum class TopBarButtonActive {
    EQUIPMENT, SPELLS
}

@Preview
@Composable
fun HeroTopBarPreview() {
    DunglerTheme(darkTheme = true) {
        HeroTopBar(
            heroState = HeroState.MOCK,
            topBarListener = TopBarListener.EMPTY,
            activeButton = TopBarButtonActive.EQUIPMENT,
        )
    }
}