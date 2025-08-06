package ru.lemonapes.dungler.domain_models.actions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable

@Serializable
@Immutable
@SerialName("HOME_HEAL_EFFECT")
class HomeHealAction(val healAmount: Int) : Action