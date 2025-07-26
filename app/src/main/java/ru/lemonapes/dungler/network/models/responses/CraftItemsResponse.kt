package ru.lemonapes.dungler.network.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.FoodId
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.domain_models.StatId
import ru.lemonapes.dungler.network.models.HeroStateDto

@Serializable
data class CraftItemsResponse(
    @SerialName("create_items")
    val createItems: List<CreateItemDto>? = null,
    @SerialName("food_items")
    val foodItems: List<CreateFoodDto>? = null,
    @SerialName("upgrade_items")
    val upgradeItems: List<UpgradeItemDto>? = null,
    val reagents: Map<ReagentId, Int>? = null,
    @SerialName("hero_state") val serverHeroState: HeroStateDto,
)

@Serializable
data class CreateItemDto(
    @SerialName("gear_id")
    val gearId: GearId = GearId.UNKNOWN_ITEM,
    val stats: Map<StatId, Int>? = null,
    val reagents: Map<ReagentId, Int>? = null,
)

@Serializable
data class CreateFoodDto(
    @SerialName("food_id")
    val foodId: FoodId,

    @SerialName("health_regen_amount")
    val healthRegenAmount: Int,

    @SerialName("health_regen_ticks")
    val healthRegenTicks: Int,

    @SerialName("reagents")
    val reagents: Map<ReagentId, Int>,
)

@Serializable
data class UpgradeItemDto(
    @SerialName("gear_id")
    val gearId: GearId,
    val level: Int,
    @SerialName("gear_type")
    val gearType: String? = null,
    val stats: Map<StatId, Int>? = null,
    @SerialName("next_stats")
    val nextStats: Map<StatId, Int>? = null,
    val reagents: Map<ReagentId, Int>? = null,
)
