package ru.lemonapes.dungler.ui.models

import androidx.compose.runtime.Immutable
import ru.lemonapes.dungler.domain_models.actions.Action
import ru.lemonapes.dungler.domain_models.actions.AttackResult
import ru.lemonapes.dungler.domain_models.actions.EatingEffectAction
import ru.lemonapes.dungler.domain_models.actions.EnemyAttackAction

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
        is Action.HeroSingleDamageAction -> {
            damageData.takeIf { damageData.targetIndex == enemyIndex }?.run {
                AttackVO(
                    hpValue = -heroPureDamage,
                    attackResult = attackResult,
                )
            }
        }

        is Action.HeroMassiveDamageAction -> {
            damageDataList.firstOrNull { it.targetIndex == enemyIndex }?.run {
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
        is EnemyAttackAction -> AttackVO(-pureDamage, attackResult)
        is EatingEffectAction -> HealVO(healAmount)
        else -> null
    }
}