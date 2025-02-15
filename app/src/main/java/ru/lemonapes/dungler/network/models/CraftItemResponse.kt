package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.domain_models.StatId

@Serializable
data class CraftItemsResponse(
    @SerialName("create_items")
    val createItems: List<ServerCreateItem>,
    @SerialName("upgrade_items")
    val upgradeItems: List<ServerUpgradeItem>,
    val reagents: Map<ReagentId, Int>,
)

@Serializable
data class ServerCreateItem(
    @SerialName("gear_id")
    val gearId: GearId = GearId.UNKNOWN_ITEM,
    val stats: Map<StatId, Int>,
    val reagents: Map<ReagentId, Int>,
)

@Serializable
data class ServerUpgradeItem(
    @SerialName("gear_id")
    val gearId: GearId,
    val level: Int,
    val stats: Map<StatId, Int>,
    @SerialName("next_stats")
    val nextStats: Map<StatId, Int>,
    val reagents: Map<ReagentId, Int>,
)
