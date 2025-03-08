package ru.lemonapes.dungler.navigation.dungeon

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.ViewModelAction
import ru.lemonapes.dungler.repositories.HeroStateRepository
import javax.inject.Inject

interface DungeonViewModelAction : ViewModelAction {
}

@HiltViewModel
class DungeonViewModel @Inject constructor(
    heroStateRepository: HeroStateRepository,
) : ParentViewModel<DungeonState>(DungeonState.EMPTY, heroStateRepository),
    DungeonViewModelAction {

    init {
        viewModelScope.launch {
            heroStateRepository.heroStateFlow.collect { heroState ->
                updateState { currentState ->
                    currentState.copy(
                        isLoading = heroState.isLoading,
                        enemies = heroState.enemies
                    )
                }
            }
        }
    }

    override fun actionStart() = withActualState {
        /*actionSetLoading()
        updateState { state ->
            state.copy(isLoading = false)
        }*/
    }

    override fun actionSetLoading() = updateState { oldState ->
        oldState.copy(isLoading = true, error = null)
    }

    override fun actionError(throwable: Throwable) {
        updateState { oldState ->
            oldState.copy(error = throwable, isLoading = false)
        }
    }
}
