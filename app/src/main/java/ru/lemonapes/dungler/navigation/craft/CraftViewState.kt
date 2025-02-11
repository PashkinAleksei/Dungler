package ru.lemonapes.dungler.navigation.craft

import ru.lemonapes.dungler.navigation.domain_models.DomainCreateItem
import ru.lemonapes.dungler.navigation.domain_models.DomainUpgradeItem
import ru.lemonapes.dungler.parent_store.State

data class CraftViewState(
    val createItems: List<DomainCreateItem> = emptyList(),
    val upgradeItems : List<DomainUpgradeItem> = emptyList(),
    val reagents: Map<String, Int> = emptyMap(),
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