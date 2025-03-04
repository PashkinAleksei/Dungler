package ru.lemonapes.dungler.stores

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    companion object {

    }
}
