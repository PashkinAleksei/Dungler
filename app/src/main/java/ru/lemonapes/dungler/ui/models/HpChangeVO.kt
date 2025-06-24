package ru.lemonapes.dungler.ui.models

import androidx.compose.runtime.Immutable
import ru.lemonapes.dungler.domain_models.AttackResult
import ru.lemonapes.dungler.hero_state.Action

@Immutable
sealed interface HpChangeVO {
    val hpValue: Int
}

data class AttackVO(
    override val hpValue: Int,
    val attackResult: AttackResult,
) : HpChangeVO

data class HealVO(
    override val hpValue: Int,
) : HpChangeVO

fun Action.getLastDamageToEnemy(enemyIndex: Int): AttackVO? =
    when (this) {
        is Action.SingleDamage -> {
            damageData.takeIf { damageData.targetIndex == enemyIndex }?.run {
                AttackVO(
                    hpValue = -heroPureDamage,
                    attackResult = attackResult,
                )
            }
        }

        is Action.MassiveDamage -> {
            damageData.firstOrNull { it.targetIndex == enemyIndex }?.run {
                AttackVO(
                    hpValue = -heroPureDamage,
                    attackResult = attackResult,
                )
            }
        }

        else -> null
    }


fun Action.getLastHeroHpChange(): HpChangeVO? {
    return when (this) {
        is Action.EnemyAttackAction -> AttackVO(-pureDamage, attackResult)
        is Action.EatingEffectAction -> HealVO(healAmount)
        else -> null
    }
}