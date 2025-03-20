package ru.lemonapes.dungler.main_activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun DungeonTopBar(
    modifier: Modifier = Modifier,
    onExitClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.BottomEnd),
            onClick = onExitClick
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.door_open),
                contentDescription = stringResource(R.string.dungeon_exit),
                tint = LocalThemeColors.current.primaryTextColor
            )
        }
    }
} 