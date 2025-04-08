package ru.lemonapes.dungler.navigation.dungeon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.image_views.ImageWithCounter
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun DungeonView(
    modifier: Modifier = Modifier,
    state: DungeonState,
    heroState: HeroState,
    listener: DungeonListener,
) {
    StateCheck(
        modifier = modifier,
        state = state,
        listener = listener.stateListener
    ) {
        Column {
            heroState.HeroRowView()
            if (heroState.isEating) {
                Row(
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .fillMaxSize(),
                ) {
                    Spacer(Modifier.weight(1f))
                    Image(
                        modifier = Modifier.weight(1f),
                        painter = painterResource(R.drawable.eating_process),
                        contentDescription = stringResource(R.string.eating_process_label),
                        contentScale = ContentScale.Fit,
                    )
                    Spacer(Modifier.weight(1f))
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    state.enemies?.let { enemies ->
                        items(enemies) { enemy ->
                            EnemyView(enemy = enemy)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroState.HeroRowView() {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .height(IntrinsicSize.Max)
    ) {
        Spacer(Modifier.weight(0.8f))
        HeroView(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.8f)
        ) {
            equippedFood?.let { equippedFood ->
                ImageWithCounter(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .padding(horizontal = 24.dp)
                        .align(Alignment.Center)
                        .padding(2.dp)
                        .background(LocalThemeColors.current.imageBackground),
                    painter = painterResource(equippedFood.id.image),
                    counter = equippedFood.count,
                    contentDescription = stringResource(equippedFood.id.foodName),
                )
            } ?: Image(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center)
                    .padding(2.dp)
                    .background(LocalThemeColors.current.imageBackground),
                painter = painterResource(R.drawable.food_disabled),
                contentDescription = stringResource(R.string.equipped_food),
            )
        }
    }
}

@Preview
@Composable
private fun DungeonScreenPreview() {
    DunglerTheme(darkTheme = true) {
        DungeonView(
            state = DungeonState.MOCK,
            heroState = HeroState.MOCK,
            listener = DungeonListener.EMPTY
        )
    }
}

@Preview
@Composable
private fun DungeonScreenNoFoodPreview() {
    DunglerTheme(darkTheme = true) {
        DungeonView(
            state = DungeonState.MOCK,
            heroState = HeroState.MOCK.copy(equippedFood = null),
            listener = DungeonListener.EMPTY
        )
    }
}

@Preview
@Composable
private fun DungeonScreenEatingPreview() {
    DunglerTheme(darkTheme = true) {
        DungeonView(
            state = DungeonState.MOCK,
            heroState = HeroState.EATING_MOCK,
            listener = DungeonListener.EMPTY
        )
    }
}
