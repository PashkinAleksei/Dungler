package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.network.models.actions.ActionDto

@Serializable
data class ServerHeroStateResponse(
    @SerialName("hero_state") val serverHeroState: HeroStateDto,
)

@Serializable
data class HeroStateDto(
    @SerialName("level") val level: Int? = null,
    @SerialName("health") val health: Int? = null,
    @SerialName("total_health") val totalHealth: Int? = null,
    @SerialName("experience") val experience: Int? = null,
    @SerialName("total_experience") val totalExperience: Int? = null,
    @SerialName("hall_number") val hallNumber: Int? = null,
    @SerialName("district_string_id") val districtStringId: String? = null,
    @SerialName("dungeon_string_id") val dungeonStringId: String? = null,
    @SerialName("actions") val actions: List<ActionDto> = emptyList(),
    @SerialName("equipped_food") val equippedFood: ServerFood? = null,
    @SerialName("enemies") val enemies: List<ServerEnemy> = emptyList(),
    @SerialName("skill_equipment") val skillsEquipment: SkillsEquipmentDto? = null,

    // @SerialName("enemies") val Enemies: List<Enemy> = emptyList(),
    // @SerialName("actions") val Actions: List<Actions> = emptyList(),
)

@Serializable
data class SkillsEquipmentDto(
    @SerialName("skill_one") val skillOne: SkillSlotDataDto? = null,
    @SerialName("skill_two") val skillTwo: SkillSlotDataDto? = null,
)

@Serializable
data class SkillSlotDataDto(
    @SerialName("skill_id") val skillId: SkillId,
    @SerialName("cooldown") val cooldown: Int,
    @SerialName("is_active") val isActive: Boolean,
)

@Serializable
enum class ActionType {
    TAKE_LOOT, NEXT_HALL, HEAL_EFFECT, HERO_IS_DEAD, EATING_EFFECT, HERO_ATTACK, ENEMY_ATTACK, ACTUAL_STATE, SKILL_ACTION,
}