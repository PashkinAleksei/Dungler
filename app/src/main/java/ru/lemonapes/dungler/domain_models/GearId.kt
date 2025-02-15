package ru.lemonapes.dungler.domain_models

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Immutable
@Serializable
enum class GearId(
    @StringRes val gearName: Int,
    val gearType: GearType,
) {
    UNKNOWN_ITEM(R.string.unknown_item_name, GearType.UNKNOWN),

    @SerialName("green_knight_helm")
    GREEN_KNIGHT_HELM(R.string.green_knight_helm_name, GearType.HELM),

    @SerialName("green_knight_shoulders")
    GREEN_KNIGHT_SHOULDERS(R.string.green_knight_shoulders_name, GearType.SHOULDERS),

    @SerialName("green_knight_chest")
    GREEN_KNIGHT_CHEST(R.string.green_knight_chest_name, GearType.CHEST),

    @SerialName("green_knight_gloves")
    GREEN_KNIGHT_GLOVES(R.string.green_knight_gloves_name, GearType.GLOVES),

    @SerialName("green_knight_legs")
    GREEN_KNIGHT_LEGS(R.string.green_knight_pants_name, GearType.LEGS),

    @SerialName("green_knight_boots")
    GREEN_KNIGHT_BOOTS(R.string.green_knight_boots_name, GearType.BOOTS),

    @SerialName("green_knight_sword")
    GREEN_KNIGHT_SWORD(R.string.green_knight_sword_name, GearType.WEAPON),


    @SerialName("test_knight_chest")
    TEST_KNIGHT_CHEST(R.string.test_knight_chest_name, GearType.CHEST),
}