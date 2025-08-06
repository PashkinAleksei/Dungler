package ru.lemonapes.dungler.domain_models.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable

@Serializable
@SerialName("ENEMY_ATTACK")
@Immutable
class EnemyAttackAction(
    val enemyIndex: Int,
    val pureDamage: Int,
    val attackResult: AttackResult,
) : Action