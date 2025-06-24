package ru.lemonapes.dungler.domain_models

import kotlinx.serialization.Serializable

@Serializable
enum class AttackResult {
    SUCCESSFULL, MISS, DODGE, PARRY,
}
