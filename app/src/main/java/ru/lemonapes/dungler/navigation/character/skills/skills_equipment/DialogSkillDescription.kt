package ru.lemonapes.dungler.navigation.character.skills.skills_equipment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun DialogSkillDescription(
    dialogDescriptionData: DialogDescriptionData,
    listener: SkillsEquipmentListener,
) {
    Dialog(
        onDismissRequest = listener.onDialogDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Row {
                    Image(
                        modifier = Modifier
                            .size(86.dp)
                            .background(
                                LocalThemeColors.current.imageBackground,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                2.dp, LocalThemeColors.current.bordersColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(4.dp),
                        painter = painterResource(dialogDescriptionData.skill.image),
                        contentDescription = stringResource(id = R.string.reagent_icon_description),
                    )
                    UIText(
                        modifier = Modifier
                            .padding(start = 12.dp, top = 4.dp, bottom = 6.dp)
                            .align(Alignment.CenterVertically),
                        text = stringResource(dialogDescriptionData.skill.skillName),
                        textStyle = LocalThemeTypographies.current.regular18
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 16.dp),
                    thickness = 2.dp,
                    color = LocalThemeColors.current.secondaryTextColor,
                )
                UIText(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 4.dp, bottom = 6.dp),
                    text = stringResource(dialogDescriptionData.skill.skillDescription),
                    textStyle = LocalThemeTypographies.current.regular18
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                ) {
                    Button(
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .align(Alignment.Center),
                        onClick = { listener.onChangeSkillClick(dialogDescriptionData.skillSlot) }
                    ) {
                        UIText(
                            textStyle = LocalThemeTypographies.current.regular20,
                            text = stringResource(R.string.change_item)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DialogViewGearDescriptionViewPreview() {
    DunglerTheme(darkTheme = true) {
        DialogSkillDescription(
            dialogDescriptionData = DialogDescriptionData.MOCK,
            listener = SkillsEquipmentListener.MOCK,
        )
    }
}