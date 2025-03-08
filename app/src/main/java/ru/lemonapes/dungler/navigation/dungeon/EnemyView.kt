package ru.lemonapes.dungler.navigation.dungeon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun EnemyView(
    enemy: Enemy,
    modifier: Modifier = Modifier,
) {
    Card(modifier, shape = RoundedCornerShape(12.dp)) {
        Box(
            modifier = modifier
                .padding(6.dp)
                .aspectRatio(1f)
                .fillMaxWidth(),
        ) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                Image(
                    painter = painterResource(enemy.image),
                    contentDescription = stringResource(R.string.enemy),
                    contentScale = ContentScale.Crop,
                )
            }
            UIText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
                    .padding(top = 4.dp),
                text = stringResource(id = enemy.enemyId.enemyName),
                textStyle = LocalThemeTypographies.current.regular14,
                textAlign = TextAlign.Center,
            )
        }
    }
}


@Preview
@Composable
fun EnemyViewPreview() {
    DunglerTheme(darkTheme = true) {
        EnemyView(enemy = Enemy.MOCK)
    }
}
