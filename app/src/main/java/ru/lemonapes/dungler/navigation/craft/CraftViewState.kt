package ru.lemonapes.dungler.navigation.craft

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.domain_models.CreateGear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.domain_models.UpgradeGear
import ru.lemonapes.dungler.parent_view_model.State

data class CraftViewState(
    override val error: Throwable? = null,
    override val isLoading: Boolean = true,

    val createItems: ImmutableList<CreateGear> = persistentListOf(),
    val upgradeItems: ImmutableList<UpgradeGear> = persistentListOf(),
    val reagents: ImmutableMap<ReagentId, Int> = persistentMapOf(),
    val switchState: CraftSwitchState = CraftSwitchState.CREATE,

    ) : State {

    enum class CraftSwitchState {
        CREATE, UPGRADE
    }

    companion object {
        fun getEmpty() = CraftViewState()
    }
}