package ru.lemonapes.dungler.main_activity

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.State
import ru.lemonapes.dungler.parent_view_model.ViewModelAction
import ru.lemonapes.dungler.repositories.HeroStateRepository
import javax.inject.Inject

data class MainActivityState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    val rootRoute: MainRoute = MainRoute.MAIN,
) : State {
    companion object {
        val EMPTY = MainActivityState()
    }
}

enum class MainRoute {
    DUNGEON, MAIN
}

interface MainViewModelAction : ViewModelAction {
    fun actionStopPolling()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    heroStateRepository: HeroStateRepository,
) : ParentViewModel<MainActivityState>(MainActivityState.EMPTY, heroStateRepository), MainViewModelAction {

    init {
        viewModelScope.launch {
            heroStateRepository.heroStateFlow.collect { heroState ->
                updateState { state ->
                    if (heroState.dungeonState != null) {
                        state.copy(rootRoute = MainRoute.DUNGEON)
                    } else {
                        state.copy(rootRoute = MainRoute.MAIN)
                    }
                }
            }
        }
    }

    override fun actionStopPolling() {
        heroStateRepository.stopPolling()
    }

    override fun actionStart() {
        heroStateRepository.startPolling(viewModelScope)
    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        throwable.printStackTrace()
        oldState.copy(error = throwable, isLoading = false)
    }

    override fun actionSetLoading() = updateState { oldState ->
        oldState.copy(isLoading = true, error = null)
    }
}