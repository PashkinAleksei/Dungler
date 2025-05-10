package ru.lemonapes.dungler.navigation.character.spell_equipment

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.mappers.EquipmentResponseMapper
import ru.lemonapes.dungler.network.endpoints.getEquipment
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
    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            actionSetLoading()
            val result = EquipmentResponseMapper(getEquipment())
            heroStateRepository.setNewHeroState(result.heroState)

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