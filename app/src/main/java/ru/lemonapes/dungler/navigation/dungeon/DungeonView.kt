package ru.lemonapes.dungler.navigation.dungeon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.theme.DunglerTheme

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
            Row(Modifier.padding(top = 8.dp)) {
                Spacer(Modifier.weight(0.8f))
                heroState.HeroView(Modifier.weight(1f))
                Spacer(Modifier.weight(0.8f))
            }
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
private fun DungeonScreenEatingPreview() {
    DunglerTheme(darkTheme = true) {
        DungeonView(
            state = DungeonState.MOCK,
            heroState = HeroState.EATING_MOCK,
            listener = DungeonListener.EMPTY
        )
    }
}
