package ru.lemonapes.dungler.hero_state

data class HeroDamageData(
    val heroPureDamage: Int,
    val targetIndex: Int,
) {
    companion object {
        val MOCK get() = HeroDamageData(23, 1)
    }
}