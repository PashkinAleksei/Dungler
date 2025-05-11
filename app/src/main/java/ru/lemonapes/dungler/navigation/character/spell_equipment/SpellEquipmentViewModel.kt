package ru.lemonapes.dungler.navigation.character.spell_equipment

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.mappers.HeroStateMapper
import ru.lemonapes.dungler.network.endpoints.getHeroState
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.ViewModelAction
import ru.lemonapes.dungler.repositories.HeroStateRepository
import javax.inject.Inject

interface SpellEquipmentAction : ViewModelAction {
}

@HiltViewModel
class SpellEquipmentViewModel @Inject constructor(
    heroStateRepository: HeroStateRepository,
) : ParentViewModel<SpellEquipmentViewState>(SpellEquipmentViewState.EMPTY, heroStateRepository), SpellEquipmentAction {

    val heroStateFlow = heroStateRepository.heroStateFlow

    init {
        viewModelScope.launch {
            heroStateRepository.heroStateFlow.collect { heroState ->
                updateState { currentState ->
                    currentState.copy(
                        isLoading = heroState.isLoading,
                        spellEquipment = heroState.spellEquipment
                    )
                }
            }
        }
    }

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            actionSetLoading()
            val result = getHeroState()
            val heroState = HeroStateMapper(result.serverHeroState)
            heroStateRepository.setNewHeroState(heroState)

            updateState { state ->
                state.copy(
                    isLoading = false,
                )
            }
        }
    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        oldState.copy(error = throwable, isLoading = false)
    }

    override fun actionSetLoading() = updateState { oldState ->
        oldState.copy(isLoading = true, error = null)
    }
}