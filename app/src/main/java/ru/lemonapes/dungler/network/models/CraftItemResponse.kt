package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.domain_models.ReagentId

@Serializable
data class CraftItemsResponse(
    @SerialName("create_items")
    val createItems: List<CreateItem>,
    @SerialName("upgrade_items")
    val upgradeItems: List<UpgradeItem>,
    val reagents: Map<ReagentId, Int>
)

@Serializable
data class CreateItem(
    @SerialName("gear_id")
    val gearId: GearId = GearId.UNKNOWN_ITEM,
    @SerialName("gear_type")
    val gearType: GearType,
    val stats: Map<String, Int>,
    val reagents: Map<ReagentId, Int>
)

@Serializable
data class UpgradeItem(
    @SerialName("gear_id")
    val gearId: GearId,
    val level: Int,
    @SerialName("gear_type")
    val gearType: GearType,
    val stats: Map<String, Int>,
    @SerialName("next_stats")
    val nextStats: Map<String, Int>,
    val reagents: Map<ReagentId, Int>
)
