package ru.lemonapes.dungler.domain_models

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.R

@Immutable
@Serializable
enum class StatId(
    @StringRes val statName: Int,
) {
    UNKNOWN_STAT(R.string.unknown_item_name),

    @SerialName("strength")
    STRENGTH(R.string.stat_strength),

    @SerialName("stamina")
    STAMINA(R.string.stat_stamina),

    @SerialName("agility")
    AGILITY(R.string.stat_agility),

    @SerialName("intellect")
    INTELLECT(R.string.stat_intellect),

    @SerialName("armor")
    ARMOR(R.string.stat_armor),

    @SerialName("total_health")
    TOTAL_HEALTH(R.string.stat_total_health),

    @SerialName("damage")
    DAMAGE(R.string.stat_damage),

    @SerialName("damage_min")
    DAMAGE_MIN(R.string.stat_damage_min),

    @SerialName("damage_max")
    DAMAGE_MAX(R.string.stat_damage_max),

    @SerialName("protection")
    PROTECTION(R.string.stat_protection),

    @SerialName("dodge_chance")
    DODGE_CHANCE(R.string.stat_dodge_chance),

    @SerialName("crit_chance")
    CRIT_CHANCE(R.string.stat_crit_chance),

    @SerialName("min_hero_level")
    MIN_HERO_LEVEL(R.string.min_hero_level),
}
