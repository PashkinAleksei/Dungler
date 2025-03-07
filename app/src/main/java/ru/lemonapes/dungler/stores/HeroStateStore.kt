package ru.lemonapes.dungler.stores

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.lemonapes.dungler.hero_state.HeroState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroStateStore @Inject constructor() {
    private val _heroState = MutableStateFlow(HeroState.EMPTY)
    val heroStateFlow: StateFlow<HeroState> = _heroState
    var heroState: HeroState
        get() = _heroState.value
        set(newValue) {
            _heroState.value = newValue
        }

    fun updateState(actionBlock: (oldState: HeroState) -> HeroState) {
        _heroState.update { actionBlock(_heroState.value) }
    }

    companion object {

    }
}
