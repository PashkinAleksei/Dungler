package ru.lemonapes.dungler.navigation.character.spell_equipment

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
import ru.lemonapes.dungler.domain_models.SpellId
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

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
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.weight(1f))
            SpellView(state.spellEquipment?.spellOne, listener.onSpellClick)
            Spacer(Modifier.weight(1f))
            SpellView(state.spellEquipment?.spellTwo, listener.onSpellClick)
            Spacer(Modifier.weight(2f))
        }
    }
}

@Composable
private fun SpellView(spell: SpellId?, onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth(0.5f)
            .border(4.dp, LocalThemeColors.current.bordersColor, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        spell?.let {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(LocalThemeColors.current.imageBackground),
                painter = painterResource(spell.image),
                contentDescription = stringResource(spell.spellName),
            )
        }
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