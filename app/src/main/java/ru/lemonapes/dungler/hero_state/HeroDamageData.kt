package ru.lemonapes.dungler.hero_state

import ru.lemonapes.dungler.domain_models.AttackResult

data class HeroDamageData(
    val heroPureDamage: Int,
    val targetIndex: Int,
    val attackResult: AttackResult,
) {
    companion object {
        val MOCK get() = HeroDamageData(23, 1, AttackResult.SUCCESSFULL)
    }
}