package ru.lemonapes.dungler.navigation.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SkillSlot {
    @SerialName("one")
    SKILL_SLOT_ONE,

    @SerialName("two")
    SKILL_SLOT_TWO,
}