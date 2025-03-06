package ru.lemonapes.dungler.navigation.dungeon_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun DungeonListView(
    modifier: Modifier = Modifier,
    state: DungeonListState,
    listener: DungeonListListener,
) {
    StateCheck(
        modifier = modifier,
        state = state,
        listener = listener
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            UIText(
                text = stringResource(R.string.castle_dungeons_title),
                textStyle = LocalThemeTypographies.current.regular24,
            )

            Button(
                onClick = { listener.onEnterDungeonsClick("drainage_channels") },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.enter_the_dungeons))
            }
        }
    }
}

@Preview
@Composable
private fun DungeonListScreenPreview() {
    DunglerTheme(darkTheme = true) {
        DungeonListView(state = DungeonListState.MOCK, listener = DungeonListListener.EMPTY)
    }
}