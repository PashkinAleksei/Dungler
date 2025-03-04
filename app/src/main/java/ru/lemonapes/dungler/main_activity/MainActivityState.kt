package ru.lemonapes.dungler.main_activity

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.parent_store.State
import ru.lemonapes.dungler.parent_store.ViewModelAction
import ru.lemonapes.dungler.parent_store.ViewModelStore
import ru.lemonapes.dungler.repositories.HeroStateRepository
import javax.inject.Inject

data class MainActivityState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
) : State {
    companion object {
        val EMPTY = MainActivityState()
    }
}

interface MainViewModelAction : ViewModelAction {
    fun actionStopPolling()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val heroStateRepository: HeroStateRepository,
) : ViewModelStore<MainActivityState>(MainActivityState.EMPTY), MainViewModelAction {

    override fun actionStopPolling() {
        viewModelScope.launch {
            heroStateRepository.stopPolling()
        }
    }

    override fun actionStart() {
        viewModelScope.launch {
            heroStateRepository.startPolling(this)
        }
    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        throwable.printStackTrace()
        oldState.copy(error = throwable, isLoading = false)
    }

    override fun actionSetLoading() = updateState { oldState ->
        oldState.copy(isLoading = true, error = null)
    }
}