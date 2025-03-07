package ru.lemonapes.dungler.domain_models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Immutable
@Serializable
enum class GearId(
    @StringRes val gearName: Int,
    val gearType: GearType,
    @DrawableRes val imageList: ImmutableList<Int>,
) {
    UNKNOWN_ITEM(
        gearName = R.string.unknown_item_name,
        gearType = GearType.UNKNOWN,
        imageList = persistentListOf(R.drawable.ic_error)
    ),

    @SerialName("green_knight_helm")
    GREEN_KNIGHT_HELM(
        gearName = R.string.green_knight_helm_name,
        gearType = GearType.HELM,
        imageList = persistentListOf(R.drawable.helm)
    ),

    @SerialName("green_knight_shoulders")
    GREEN_KNIGHT_SHOULDERS(
        gearName = R.string.green_knight_shoulders_name,
        gearType = GearType.SHOULDERS,
        imageList = persistentListOf(R.drawable.shoulders),
    ),

    @SerialName("green_knight_chest")
    GREEN_KNIGHT_CHEST(
        gearName = R.string.green_knight_chest_name,
        gearType = GearType.CHEST,
        imageList = persistentListOf(
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

    @SerialName("green_knight_gloves")
    GREEN_KNIGHT_GLOVES(
        gearName = R.string.green_knight_gloves_name,
        gearType = GearType.GLOVES,
        imageList = persistentListOf(
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

    @SerialName("green_knight_legs")
    GREEN_KNIGHT_LEGS(
        gearName = R.string.green_knight_pants_name,
        gearType = GearType.LEGS,
        imageList = persistentListOf(R.drawable.pants)
    ),

    @SerialName("green_knight_boots")
    GREEN_KNIGHT_BOOTS(
        gearName = R.string.green_knight_boots_name,
        gearType = GearType.BOOTS,
        imageList = persistentListOf(R.drawable.boots)
    ),

    @SerialName("green_knight_sword")
    GREEN_KNIGHT_SWORD(
        gearName = R.string.green_knight_sword_name,
        gearType = GearType.WEAPON,
        imageList = persistentListOf(R.drawable.sword)
    ),


    @SerialName("test_knight_chest")
    TEST_KNIGHT_CHEST(
        gearName = R.string.test_knight_chest_name,
        gearType = GearType.CHEST,
        imageList = persistentListOf(R.drawable.chest)
    ),
}