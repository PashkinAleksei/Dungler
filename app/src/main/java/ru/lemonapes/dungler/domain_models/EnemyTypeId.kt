package ru.lemonapes.dungler.domain_models

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Immutable
@Serializable
enum class EnemyTypeId(
    @StringRes val enemyName: Int,
    val imageList: ImmutableList<Int>,
) {
    UNKNOWN_ENEMY(
        enemyName = R.string.unknown_enemy_name,
        imageList = persistentListOf(R.drawable.ic_error)
    ),

    @SerialName("giant_rat")
    GIANT_RAT(
        enemyName = R.string.giant_rat_name,
        imageList = persistentListOf(R.drawable.img_giant_rat)
    ),

    @SerialName("bat")
    BAT(
        enemyName = R.string.bat_name,
        imageList = persistentListOf(R.drawable.img_bat)
    ),

    @SerialName("infected_raccoon")
    INFECTED_RACOON(
        enemyName = R.string.infected_raccoon_name,
        imageList = persistentListOf(R.drawable.racoon)
    ),

    @SerialName("wild_dog")
    WILD_DOG(
        enemyName = R.string.wild_dog_name,
        imageList = persistentListOf(R.drawable.img_wolf)
    ),

    @SerialName("crocolisk")
    CROCOLISK(
        enemyName = R.string.crocolisk_name,
        imageList = persistentListOf(R.drawable.img_crocolisk)
    ),
}