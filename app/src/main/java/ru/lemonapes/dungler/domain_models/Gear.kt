package ru.lemonapes.dungler.domain_models

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.R

data class Gear(
    val gearId: GearId,
    val level: Int,
    val stats: ImmutableMap<StatId, Int>,
    val image: Int,
    val isEquipped: Boolean,
) {
    companion object {
        val MOCK
            get() = Gear(
                gearId = GearId.GREEN_KNIGHT_CHEST,
                level = 3,
                stats = persistentMapOf(StatId.STAMINA to 3),
                isEquipped = false,
                image = R.drawable.green_knight_chest_10
            )
    }
}