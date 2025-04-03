package ru.lemonapes.dungler.navigation.character

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.domain_models.StatId
import ru.lemonapes.dungler.navigation.character.item_comparison_dialog.DialogEquipmentState
import ru.lemonapes.dungler.parent_view_model.State

data class CharacterViewState(
    override val error: Throwable? = null,
    override val isLoading: Boolean = true,

    val gears: ImmutableMap<GearType, Gear> = persistentMapOf(),
    val food: Food? = null,
    val stats: ImmutableMap<StatId, Int> = persistentMapOf(),
    val dialogEquipmentState: DialogEquipmentState? = null,
) : State {
    companion object {
        val EMPTY get() = CharacterViewState()
        val MOCK
            get() = CharacterViewState(
                gears = persistentMapOf(GearType.CHEST to Gear.MOCK_1),
                food = Food.MOCK_1,
                stats = persistentMapOf(
                    StatId.STRENGTH to 17,
                    StatId.STAMINA to 12,
                    StatId.AGILITY to 12,
                    StatId.INTELLECT to 12,
                    StatId.ARMOR to 4,
                    StatId.TOTAL_HEALTH to 120,
                    StatId.DAMAGE_MIN to 11,
                    StatId.DAMAGE_MAX to 15,
                    StatId.PROTECTION to 0,
                    StatId.DODGE_CHANCE to 0,
                    StatId.CRIT_CHANCE to 0
                ),
                isLoading = false
            )
    }
}
