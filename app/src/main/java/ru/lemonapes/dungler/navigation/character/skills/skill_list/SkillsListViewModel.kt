package ru.lemonapes.dungler.navigation.character.skills.skill_list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.mappers.HeroStateMapper
import ru.lemonapes.dungler.navigation.character.SkillSlot
import ru.lemonapes.dungler.network.endpoints.getSkills
import ru.lemonapes.dungler.network.endpoints.patchEquipSkill
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.UiEvent
import ru.lemonapes.dungler.parent_view_model.ViewModelAction
import ru.lemonapes.dungler.repositories.HeroStateRepository
import javax.inject.Inject

interface SkillsListAction : ViewModelAction {
    fun onSkillSelected(skillId: SkillId)
}

@HiltViewModel
class SkillsListViewModel @Inject constructor(
    heroStateRepository: HeroStateRepository,
) : ParentViewModel<SkillsListViewState>(SkillsListViewState.EMPTY, heroStateRepository),
    SkillsListAction {

    private var skillSlot: SkillSlot? = null
    fun setSkillSlot(slot: SkillSlot) {
        skillSlot = slot
    }

    override fun onSkillSelected(skillId: SkillId) {
        viewModelScope.launch(Dispatchers.IO + ceh) {
            skillSlot?.let { skillSlot ->
                actionSetLoading()
                val response = patchEquipSkill(
                    slot = skillSlot,
                    skill = skillId
                )
                val heroState = HeroStateMapper(response.serverHeroState)
                heroStateRepository.setNewHeroState(heroState)
                sendUiEvent(UiEvent.NavigateBack)
            }
        }
    }

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            actionSetLoading()
            val result = ru.lemonapes.dungler.mappers.SkillsResponseMapper(getSkills())
            heroStateRepository.setNewHeroState(result.heroState)

            updateState { state ->
                state.copy(
                    skillList = result.skills,
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