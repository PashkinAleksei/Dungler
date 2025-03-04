package ru.lemonapes.dungler.game_items_data

import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.GearData
import ru.lemonapes.dungler.domain_models.GearId

val GEAR_DATA_MAP: Map<GearId, GearData> = mapOf(
    GearId.GREEN_KNIGHT_HELM to GearData(
        listOf(R.drawable.green_knight_helm)
    ),
    GearId.GREEN_KNIGHT_SHOULDERS to GearData(
        listOf(R.drawable.shoulders)
    ),
    GearId.GREEN_KNIGHT_CHEST to GearData(
        listOf(
            R.drawable.green_knight_chest_1,
            R.drawable.green_knight_chest_2,
            R.drawable.green_knight_chest_3,
            R.drawable.green_knight_chest_4,
            R.drawable.green_knight_chest_5,
            R.drawable.green_knight_chest_6,
            R.drawable.green_knight_chest_7,
            R.drawable.green_knight_chest_8,
            R.drawable.green_knight_chest_9,
            R.drawable.green_knight_chest_10,
        )
    ),
    GearId.GREEN_KNIGHT_GLOVES to GearData(
        listOf(
            R.drawable.green_knight_gloves_1,
            R.drawable.green_knight_gloves_2,
            R.drawable.green_knight_gloves_3,
            R.drawable.green_knight_gloves_4,
            R.drawable.green_knight_gloves_5,
            R.drawable.green_knight_gloves_6,
            R.drawable.green_knight_gloves_7,
            R.drawable.green_knight_gloves_8,
            R.drawable.green_knight_gloves_9,
            R.drawable.green_knight_gloves_10,
        )
    ),
    GearId.GREEN_KNIGHT_LEGS to GearData(
        listOf(R.drawable.pants)
    ),
    GearId.GREEN_KNIGHT_BOOTS to GearData(
        listOf(R.drawable.boots)
    ),
    GearId.GREEN_KNIGHT_SWORD to GearData(
        listOf(R.drawable.sword)
    ),


    GearId.TEST_KNIGHT_CHEST to GearData(
        listOf(R.drawable.chest)
    ),
)

val DEFAULT_GEAR_DATA = GearData(
    listOf(R.drawable.ic_error)
)