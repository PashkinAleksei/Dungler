package ru.lemonapes.dungler.navigation.game_items_data

import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.navigation.domain_models.GearData
import ru.lemonapes.dungler.navigation.domain_models.GearId

val GEAR_DATA_MAP: Map<GearId, GearData> = mapOf(
    GearId.GREEN_KNIGHT_HELM to GearData(
        R.string.green_knight_helm_name,
        R.drawable.green_knight_helm
    ),
    GearId.GREEN_KNIGHT_SHOULDERS to GearData(
        R.string.green_knight_shoulders_name,
        R.drawable.shoulders
    ),
    GearId.GREEN_KNIGHT_CHEST to GearData(
        R.string.green_knight_chest_name,
        R.drawable.green_knight_chest_10
    ),
    GearId.GREEN_KNIGHT_GLOVES to GearData(
        R.string.green_knight_gloves_name,
        R.drawable.green_knight_gloves_1
    ),
    GearId.GREEN_KNIGHT_LEGS to GearData(
        R.string.green_knight_pants_name,
        R.drawable.pants
    ),
    GearId.GREEN_KNIGHT_BOOTS to GearData(
        R.string.green_knight_boots_name,
        R.drawable.boots
    ),
    GearId.GREEN_KNIGHT_SWORD to GearData(
        R.string.green_knight_sword_name,
        R.drawable.sword
    )
)

val DEFAULT_GEAR_DATA = GearData(
    R.string.unknown_gear_name,
    R.drawable.ic_error
)