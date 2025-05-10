package ru.lemonapes.dungler.navigation.character.spell_equipment

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.theme.DunglerTheme

@Composable
fun SpellEquipmentView(
    modifier: Modifier = Modifier,
    state: SpellEquipmentViewState,
    listener: SpellEquipmentListener,
) {
    StateCheck(
        modifier = modifier,
        state = state,
        listener = listener.stateListener
    ) {

    }
}

@Preview
@Composable
private fun CharacterViewPreview() {
    DunglerTheme(darkTheme = true) {
        SpellEquipmentView(
            state = SpellEquipmentViewState.MOCK,
            listener = SpellEquipmentListener.MOCK,
        )
    }
}