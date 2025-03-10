package ru.lemonapes.dungler.domain_models

import androidx.annotation.DrawableRes
import ru.lemonapes.dungler.R

data class Enemy(
    @DrawableRes val image: Int,
    val enemyId: EnemyTypeId,
    val health: Int,
    val damageMin: Int,
    val damageMax: Int,
    val totalHealth: Int,
    val level: Int,
) {
    companion object {
        val MOCK
            get() = Enemy(
                image = R.drawable.img_giant_rat,
                enemyId = EnemyTypeId.GIANT_RAT,
                health = 20,
                totalHealth = 100,
                damageMin = 1,
                damageMax = 2,
                level = 1
            )
    }
}