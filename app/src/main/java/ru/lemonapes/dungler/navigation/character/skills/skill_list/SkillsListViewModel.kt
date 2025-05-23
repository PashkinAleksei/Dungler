package ru.lemonapes.dungler.navigation.character.skills.skill_list

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.mappers.SkillsResponseMapper
import ru.lemonapes.dungler.network.endpoints.getSkills
import ru.lemonapes.dungler.parent_view_model.ParentViewModel
import ru.lemonapes.dungler.parent_view_model.ViewModelAction
import ru.lemonapes.dungler.repositories.HeroStateRepository
import javax.inject.Inject

interface SkillsListAction : ViewModelAction {
}

@HiltViewModel
class SkillsListViewModel @Inject constructor(
    heroStateRepository: HeroStateRepository,
) : ParentViewModel<SkillsListViewState>(SkillsListViewState.EMPTY, heroStateRepository),
    SkillsListAction {

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            actionSetLoading()
            val result = SkillsResponseMapper(getSkills())
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