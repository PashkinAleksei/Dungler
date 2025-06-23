package ru.lemonapes.dungler.navigation.dungeon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.Enemy
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun EnemyView(
    enemy: Enemy,
    damageToEnemy: Int?,
    modifier: Modifier = Modifier,
) {
    Card(modifier, shape = RoundedCornerShape(12.dp)) {
        Box(
            modifier = modifier
                .padding(3.dp)
                .aspectRatio(0.75f)
                .fillMaxWidth(),
        ) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                Image(
                    painter = painterResource(enemy.image),
                    contentDescription = stringResource(R.string.enemy),
                    contentScale = ContentScale.Crop,
                )
            }
            val enemyName = stringResource(id = enemy.enemyId.enemyName).run {
                if (enemy.isAlive) this else this + " " + stringResource(R.string.corpse)
            }
            UIText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
                    .padding(top = 4.dp),
                text = enemyName,
                textStyle = LocalThemeTypographies.current.regular14Compact,
                textAlign = TextAlign.Center,
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        enemy.LootLabel(
                            Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 6.dp)
                        )
                    }
                    Row(Modifier.padding(top = 2.dp)) {
                        enemy.LevelLabel(Modifier.padding(start = 6.dp))
                        Spacer(Modifier.weight(1f))
                        enemy.AttackLabel(Modifier.padding(end = 6.dp))
                    }
                    enemy.HealthBar()
                }
                val damageText = damageToEnemy?.let { "-$it" } ?: ""
                UIText(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = damageText,
                    color = Color.Red,
                    textStyle = LocalThemeTypographies.current.regular28,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun Enemy.LevelLabel(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = LocalThemeColors.current.surfaceSemiTransparentColor
    ) {
        UIText(
            modifier = Modifier
                .padding(horizontal = 4.dp),
            text = stringResource(
                R.string.dungeon_screen_level,
                level,
            ),
            textStyle = LocalThemeTypographies.current.regular14,
        )
    }
}

@Composable
private fun Enemy.AttackLabel(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = LocalThemeColors.current.surfaceSemiTransparentColor
    ) {
        UIText(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = stringResource(
                R.string.dungeon_screen_damage,
                damageMin,
                damageMax
            ),
            textStyle = LocalThemeTypographies.current.regular14,
        )
    }
}

@Composable
private fun Enemy.LootLabel(modifier: Modifier = Modifier) {
    if (!isAlive && loot.isNotEmpty()) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(4.dp),
            color = LocalThemeColors.current.surfaceSemiTransparentColor
        ) {
            Image(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(R.drawable.ic_backpack),
                colorFilter = ColorFilter.tint(LocalThemeColors.current.primaryTextColor),
                contentDescription = stringResource(id = R.string.loot_label),
            )
        }
    }
}

@Composable
private fun Enemy.HealthBar(
    modifier: Modifier = Modifier,
) {
    val healthProgress = health.toFloat().div(totalHealth.toFloat())
    val healthText = "${health}/${totalHealth}"

    Surface(
        modifier = modifier
            .padding(horizontal = 6.dp)
            .padding(top = 2.dp)
            .padding(bottom = 6.dp)
            .fillMaxWidth()
            .height(16.dp),
        shape = RoundedCornerShape(4.dp)
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
        if (isAlive) {
            UIText(
                modifier = Modifier
                    .fillMaxSize(),
                textAlign = TextAlign.Center,
                text = healthText,
                color = LocalThemeColors.current.barsTextColor,
                textStyle = LocalThemeTypographies.current.regular12
            )
        } else {
            Icon(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxHeight(),
                painter = painterResource(id = R.drawable.skull),
                contentDescription = stringResource(R.string.dungeon_exit),
                tint = LocalThemeColors.current.barsTextColor
            )
        }
    }
}

@Preview
@Composable
fun EnemyViewPreview() {
    DunglerTheme(darkTheme = true) {
        EnemyView(enemy = Enemy.DEAD_MOCK, damageToEnemy = 23)
    }
}
