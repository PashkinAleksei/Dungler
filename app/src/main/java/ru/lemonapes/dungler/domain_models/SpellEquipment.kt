package ru.lemonapes.dungler.domain_models

import kotlinx.serialization.Serializable

@Serializable
data class SpellEquipment(
    val spellOne: SpellId?,
    val spellTwo: SpellId?,
) {
    companion object {
        val EMPTY
            get() = SpellEquipment(null, null)
        val MOCK
            get() = SpellEquipment(SpellId.WHIRLWIND, SpellId.HEROIC_STRIKE)
    }
}