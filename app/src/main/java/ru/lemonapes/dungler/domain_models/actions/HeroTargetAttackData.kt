package ru.lemonapes.dungler.domain_models.actions

import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable

@Serializable
@Immutable
class HeroTargetAttackData(
    val heroPureDamage: Int,
    val targetIndex: Int,
    val attackResult: AttackResult,
) {
    companion object {
        val MOCK get() = HeroTargetAttackData(23, 1, AttackResult.SUCCESSFULL)
    }
}