package ru.lemonapes.dungler.navigation.dungeon

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.mappers.HeroStateMapper
import ru.lemonapes.dungler.navigation.SkillSlotNum
import ru.lemonapes.dungler.network.endpoints.patchActivateSkill
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

    val heroStateFlow = heroStateRepository.heroStateFlow

    init {
        viewModelScope.launch {
            heroStateRepository.heroStateFlow.collect { heroState ->
                updateState { currentState ->
                    currentState.copy(
                        isLoading = heroState.isLoading,
                        enemies = heroState.dungeonState?.enemies
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

    fun activateSkill(slot: SkillSlotNum) {
        viewModelScope.launch {
            try {
                val currentHeroState = heroStateFlow.value
                val lastExecutedAction = currentHeroState.lastExecutedAction
                val isCurrentlyActive = when (slot) {
                    SkillSlotNum.SLOT_ONE -> currentHeroState.skillsEquipment.skillOne?.isActive
                    SkillSlotNum.SLOT_TWO -> currentHeroState.skillsEquipment.skillTwo?.isActive
                } ?: return@launch

                val newState = patchActivateSkill(slot, !isCurrentlyActive)
                val mappedState = HeroStateMapper(newState.serverHeroState, lastExecutedAction)
                heroStateRepository.setNewHeroState(mappedState)
            } catch (e: Exception) {
                actionError(e)
            }
        }
    }
}
