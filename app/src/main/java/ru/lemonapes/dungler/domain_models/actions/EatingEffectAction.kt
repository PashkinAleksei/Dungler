package ru.lemonapes.dungler.domain_models.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable

@Serializable
@SerialName("EATING_EFFECT")
@Immutable
class EatingEffectAction(
    val healAmount: Int,
    val reduceFood: Boolean,
) : Action