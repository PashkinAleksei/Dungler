package ru.lemonapes.dungler.navigation.character.skills.skills_equipment

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.navigation.SkillSlotNum
import ru.lemonapes.dungler.navigation.composableWithBars
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.skillsEquipmentNavigation(navController: NavController) {
    composableWithBars<Screens.EquipmentSkills> {
        val model: SkillsEquipmentViewModel = hiltViewModel()
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionOnStart(model::actionStart)
        var dialogDescriptionData by remember { mutableStateOf<DialogDescriptionData?>(null) }
        SkillsEquipmentView(
            state = state,
            dialogDescriptionData = dialogDescriptionData,
            listener = SkillsEquipmentListener(
                onSkillClick = { skillSlot, skill ->
                    skill?.let {
                        dialogDescriptionData = DialogDescriptionData(skillSlot, skill)
                    } ?: run {
                        navController.navigate(Screens.SkillList(skillSlot))
                    }
                },
                onDialogDismiss = {
                    dialogDescriptionData = null
                },
                onChangeSkillClick = { skillSlot ->
                    dialogDescriptionData = null
                    navController.navigate(Screens.SkillList(skillSlot))
                },
                stateListener = StateListener(
                    onRetryClick = {
                        model.actionStart()
                    },
                ),
            )
        )
    }
}

class SkillsEquipmentListener(
    val onSkillClick: (SkillSlotNum, SkillId?) -> Unit,
    val onDialogDismiss: () -> Unit,
    val onChangeSkillClick: (SkillSlotNum) -> Unit,
    val stateListener: StateListener,
) {
    companion object {
        val MOCK
            get() = SkillsEquipmentListener(
                onSkillClick = { _, _ -> },
                onDialogDismiss = {},
                onChangeSkillClick = {},
                stateListener = StateListener.MOCK,
            )
    }
}