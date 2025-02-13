package ru.lemonapes.dungler.navigation.craft

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.domain_models.CreateGear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.domain_models.UpgradeGear
import ru.lemonapes.dungler.parent_store.State

data class CraftViewState(
    val createItems: List<CreateGear> = emptyList(),
    val upgradeItems: List<UpgradeGear> = emptyList(),
    val reagents: ImmutableMap<ReagentId, Int> = persistentMapOf(),
    val switchState: CraftSwitchState = CraftSwitchState.CREATE,
    val error: Throwable? = null,
) : State {

    enum class CraftSwitchState {
        CREATE, UPGRADE
    }

    companion object {
        fun getEmpty() = CraftViewState()
    }
}