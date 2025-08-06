package ru.lemonapes.dungler.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SkillSlotNum {
    @SerialName("SLOT_ONE")
    SLOT_ONE,

    @SerialName("SLOT_TWO")
    SLOT_TWO,
}