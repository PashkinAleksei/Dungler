package ru.lemonapes.dungler.navigation.character.skills.skills_equipment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.navigation.character.SkillSlot
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun SkillsEquipmentView(
    modifier: Modifier = Modifier,
    state: SkillsEquipmentViewState,
    listener: SkillsEquipmentListener,
    dialogDescriptionData: DialogDescriptionData? = null,
) {
    StateCheck(
        modifier = modifier,
        state = state,
        listener = listener.stateListener
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.weight(1f))
            SkillView(state.skillsEquipment?.skillOne) {
                listener.onSkillClick(
                    SkillSlot.SKILL_SLOT_ONE,
                    state.skillsEquipment?.skillOne
                )
            }
            Spacer(Modifier.weight(1f))
            SkillView(state.skillsEquipment?.skillTwo) {
                listener.onSkillClick(
                    SkillSlot.SKILL_SLOT_TWO,
                    state.skillsEquipment?.skillTwo
                )
            }
            Spacer(Modifier.weight(2f))
        }
        if (dialogDescriptionData != null) {
            DialogSkillDescription(dialogDescriptionData, listener)
        }
    }
}

@Composable
private fun SkillView(skill: SkillId?, onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth(0.5f)
            .border(4.dp, LocalThemeColors.current.bordersColor, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        skill?.let {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(LocalThemeColors.current.imageBackground),
                painter = painterResource(skill.image),
                contentDescription = stringResource(skill.skillName),
            )
        }
    }
}

@Preview
@Composable
private fun CharacterViewPreview() {
    DunglerTheme(darkTheme = true) {
        SkillsEquipmentView(
            state = SkillsEquipmentViewState.MOCK,
            dialogDescriptionData = null,
            listener = SkillsEquipmentListener.MOCK,
        )
    }
}