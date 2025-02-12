package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.navigation.domain_models.GearId
import ru.lemonapes.dungler.navigation.domain_models.GearType
import ru.lemonapes.dungler.navigation.domain_models.ReagentId

@Serializable
data class CreateItemsResponse(
    val items: List<CreateItem>,
    val reagents: HashMap<String, Int>
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
