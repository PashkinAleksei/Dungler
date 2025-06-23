package ru.lemonapes.dungler.navigation.dungeon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.SelectedSkillData
import ru.lemonapes.dungler.domain_models.SkillsEquipment
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.navigation.SkillSlot
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.image_views.ImageWithCounter
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

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
        Box(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                if (heroState.isEating) {
                    Row(
                        modifier = Modifier
                            .padding(top = 48.dp)
                            .fillMaxWidth(),
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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        state.enemies?.let { enemies ->
                            itemsIndexed(enemies) { index, enemy ->
                                EnemyView(enemy = enemy, damageToEnemy = heroState.getLastDamageToEnemy(index))
                            }
                        }
                    }
                }
                heroState.HeroRowView(
                    modifier = modifier.padding(top = 16.dp, bottom = 32.dp),
                    onSkillClick = listener.onSkillClick
                )
            }
        }
    }
}

@Composable
private fun HeroState.HeroRowView(
    modifier: Modifier = Modifier,
    onSkillClick: (SkillSlot) -> Unit,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
    ) {
        SkillsView(
            modifier = Modifier.weight(0.8f),
            skillsEquipment = skillsEquipment,
            onSkillClick = onSkillClick
        )
        HeroView(Modifier.weight(1f))
        FoodView(Modifier.weight(0.8f), equippedFood)
    }
}

@Composable
private fun FoodView(modifier: Modifier = Modifier, equippedFood: Food?) {
    Box(modifier = modifier.fillMaxHeight()) {
        val imagePadding = PaddingValues(4.dp)
        equippedFood?.let { equippedFood ->
            ImageWithCounter(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .padding(horizontal = 24.dp)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LocalThemeColors.current.imageBackground),
                painter = painterResource(equippedFood.id.image),
                counter = equippedFood.count,
                contentDescription = stringResource(equippedFood.id.foodName),
                imagePadding = imagePadding
            )
        } ?: ImageWithCounter(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(horizontal = 24.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(12.dp))
                .background(LocalThemeColors.current.imageBackground),
            painter = painterResource(R.drawable.food_disabled),
            counter = null,
            contentDescription = stringResource(R.string.equipped_food),
            imagePadding = imagePadding
        )
    }
}

@Composable
private fun SkillsView(
    modifier: Modifier = Modifier,
    skillsEquipment: SkillsEquipment,
    onSkillClick: (SkillSlot) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        SkillView(modifier = Modifier.weight(0.5f),
            skillData = skillsEquipment.skillOne,
            onSkillClick = { onSkillClick(SkillSlot.SKILL_SLOT_ONE) }
        )
        SkillView(
            modifier = Modifier.weight(0.5f),
            skillData = skillsEquipment.skillTwo,
            onSkillClick = { onSkillClick(SkillSlot.SKILL_SLOT_TWO) }
        )
    }
}

@Composable
private fun SkillView(
    modifier: Modifier = Modifier,
    skillData: SelectedSkillData?,
    onSkillClick: () -> Unit,
) {
    Box(modifier = modifier) {
        skillData?.let {
            val shape = RoundedCornerShape(12.dp)
            val imageModifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(horizontal = 24.dp)
                .align(Alignment.BottomCenter)
                .clip(shape)
                .background(LocalThemeColors.current.imageBackground)
                .clickable(onClick = onSkillClick)
                .run {
                    if (skillData.isActive) {
                        border(
                            1.dp,
                            LocalThemeColors.current.skillActiveColor,
                            shape
                        )
                    } else {
                        this
                    }
                }



            ImageWithCounter(
                modifier = imageModifier,
                painter = painterResource(skillData.skillId.image),
                counter = null,
                contentDescription = stringResource(skillData.skillId.skillName),
            )
        } ?: ImageWithCounter(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(horizontal = 24.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(12.dp))
                .background(LocalThemeColors.current.imageBackground),
            painter = painterResource(R.drawable.default_skill),
            counter = null,
            contentDescription = stringResource(R.string.skill_empty_slot),
        )
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
private fun DungeonScreenNoFoodPreview() {
    DunglerTheme(darkTheme = true) {
        DungeonView(
            state = DungeonState.MOCK,
            heroState = HeroState.MOCK.copy(equippedFood = null),
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
