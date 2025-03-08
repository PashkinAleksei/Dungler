package ru.lemonapes.dungler.navigation.dungeon_list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.mappers.HeroStateResponseMapper
import ru.lemonapes.dungler.network.endpoints.setHeroLocation
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.ViewModelAction
import ru.lemonapes.dungler.repositories.HeroStateRepository
import javax.inject.Inject

interface DungeonListViewModelAction : ViewModelAction {
    fun actionEnterDungeons(dungeonId: String)
}

@HiltViewModel
class DungeonListViewModel @Inject constructor(
    heroStateRepository: HeroStateRepository,
) : ParentViewModel<DungeonListState>(DungeonListState.EMPTY, heroStateRepository),
    DungeonListViewModelAction {

    override fun actionStart() = withActualState {
        actionSetLoading()
        updateState { state ->
            state.copy(isLoading = false)
        }
    }

    override fun actionEnterDungeons(dungeonId: String) = withActualState { state ->
        if (!state.enterDungeonInProcess) {
            updateState {
                state.copy(enterDungeonInProcess = true)
            }
            launch(Dispatchers.IO + ceh) {
                val heroState = HeroStateResponseMapper(setHeroLocation(dungeonId))
                heroStateRepository.setNewHeroState(viewModelScope, heroState)
                updateState {
                    state.copy(enterDungeonInProcess = false)
                }
            }
        }
    }

    override fun actionSetLoading() = updateState { oldState ->
        oldState.copy(isLoading = true, error = null)
    }

    override fun actionError(throwable: Throwable) {
        updateState { state ->
            state.copy(
                error = throwable,
                isLoading = false,
                enterDungeonInProcess = false
            )
        }
    }
}