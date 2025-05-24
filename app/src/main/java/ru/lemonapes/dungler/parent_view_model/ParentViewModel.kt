package ru.lemonapes.dungler.parent_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.lemonapes.dungler.network.NetworkException
import ru.lemonapes.dungler.network.models.responses.ResponseErrorCode
import ru.lemonapes.dungler.repositories.HeroStateRepository

interface State {
    val isLoading: Boolean
    val error: Throwable?
}

interface ViewModelAction {
    fun actionStart()
    fun actionSetLoading()
    fun actionError(throwable: Throwable)
}

interface Store<S : State> {
    fun observeState(): StateFlow<S>
    fun updateState(actionBlock: CoroutineScope.(oldState: S) -> S)
    fun withActualState(actionBlock: CoroutineScope.(oldState: S) -> Unit)
}

abstract class ParentViewModel<S : State>(
    initialState: S,
    protected val heroStateRepository: HeroStateRepository,
) : ViewModel(), Store<S>, ViewModelAction {
    open val ceh = CoroutineExceptionHandler { _, throwable ->
        throwable.handleResponseError()
        throwable.printStackTrace()
    }

    private val _state = MutableStateFlow(initialState)

    override fun observeState(): StateFlow<S> = _state

    override fun withActualState(actionBlock: CoroutineScope.(oldState: S) -> Unit) =
        with(viewModelScope) { actionBlock(_state.value) }

    override fun updateState(actionBlock: CoroutineScope.(oldState: S) -> S) {
        _state.update { with(viewModelScope) { actionBlock(_state.value) } }
    }

    fun Throwable.handleResponseError() {
        if (this is NetworkException) {
            when (errorCode) {
                ResponseErrorCode.HERO_IN_A_DUNGEON -> heroStateRepository.heroInDungeonError()

                ResponseErrorCode.INTERNAL_ERROR -> actionError(this)
                ResponseErrorCode.INVALID_REQUEST -> actionError(this)
                ResponseErrorCode.UNPROCESSABLE_REQUEST -> actionError(this)
                ResponseErrorCode.NOT_ENOUGH_REAGENTS -> actionError(this)
                ResponseErrorCode.UNKNOWN_ERROR -> actionError(this)
            }
        } else {
            actionError(this)
        }
    }
}