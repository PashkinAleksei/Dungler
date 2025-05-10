package ru.lemonapes.dungler.navigation.character.spell_equipment

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.lemonapes.dungler.navigation.Screens
import ru.lemonapes.dungler.ui.ActionLoadingOnStop
import ru.lemonapes.dungler.ui.ActionOnStart
import ru.lemonapes.dungler.ui.StateListener

fun NavGraphBuilder.spellEquipmentNavigation() {
    composable<Screens.EquipmentSpells> {
        val model: SpellEquipmentViewModel = hiltViewModel()
        val state = model.observeState().collectAsState().value
        ActionLoadingOnStop(model)
        ActionOnStart(model::actionStart)
        SpellEquipmentView(
            state = state,
            listener = SpellEquipmentListener(
                onSpellClick = {},
                stateListener = StateListener(
                    onRetryClick = {
                        model.actionStart()
                    },
                ),
            )
        )
    }
}

class SpellEquipmentListener(
    val onSpellClick: () -> Unit,
    val stateListener: StateListener,
) {
    companion object {
        val MOCK
            get() = SpellEquipmentListener(
                onSpellClick = {},
                stateListener = StateListener.MOCK,
            )
    }
}