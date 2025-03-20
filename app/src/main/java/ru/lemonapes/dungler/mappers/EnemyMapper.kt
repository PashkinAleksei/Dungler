package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.domain_models.Enemy
import ru.lemonapes.dungler.domain_models.EnemyTypeId
import ru.lemonapes.dungler.network.models.ServerEnemy

object EnemyMapper : (ServerEnemy) -> Enemy {
    override fun invoke(serverEnemy: ServerEnemy): Enemy {
        val image = serverEnemy.enemyTypeId.imageList.getOrNull(serverEnemy.level)
            ?: serverEnemy.enemyTypeId.imageList.lastOrNull()
            ?: EnemyTypeId.UNKNOWN_ENEMY.imageList.last()

        return Enemy(
            image = image,
            enemyId = serverEnemy.enemyTypeId,
            health = serverEnemy.currentHealth,
            damageMin = serverEnemy.damageMin,
            damageMax = serverEnemy.damageMax,
            totalHealth = serverEnemy.totalHealth,
            level = serverEnemy.level,
            loot = serverEnemy.loot ?: emptyMap()
        )
    }
}