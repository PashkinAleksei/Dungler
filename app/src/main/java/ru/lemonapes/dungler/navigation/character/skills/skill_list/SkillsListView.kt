package ru.lemonapes.dungler.navigation.character.skills.skill_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun SkillsListView(
    modifier: Modifier = Modifier,
    state: SkillsListViewState,
    listener: SkillsListListener,
) {
    StateCheck(
        modifier = modifier,
        state = state,
        listener = listener.stateListener
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = listener.onClose
                ) {
                    Icon(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(R.drawable.ic_cancel),
                        tint = Color.Red,
                        contentDescription = stringResource(R.string.dismiss_dialog)
                    )
                }
            }
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp)
            ) {
                items(state.skillList) { skill ->
                    SkillView(skill.skillId, listener.onSkillClick)
                }
            }
        }
    }
}

@Composable
private fun SkillView(skill: SkillId, onClick: (SkillId) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable(onClick = { onClick(skill) })
    ) {
        Image(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LocalThemeColors.current.imageBackground),
            painter = painterResource(skill.image),
            contentDescription = stringResource(skill.skillName),
        )
        UIText(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(start = 6.dp, bottom = 6.dp),
            text = skill.skillName,
            textStyle = LocalThemeTypographies.current.regular24,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun SkillsListPreview() {
    DunglerTheme(darkTheme = true) {
        SkillsListView(
            state = SkillsListViewState.MOCK,
            listener = SkillsListListener.MOCK,
        )
    }
}