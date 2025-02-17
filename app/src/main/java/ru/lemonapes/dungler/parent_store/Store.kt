package ru.lemonapes.dungler.parent_store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

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

abstract class ViewModelStore<S : State>(
    initialState: S,
) : ViewModel(), Store<S>, ViewModelAction {
    open val ceh = CoroutineExceptionHandler { _, throwable ->
        actionError(throwable)
    }

    private val _state = MutableStateFlow(initialState)

    override fun observeState(): StateFlow<S> = _state

    override fun withActualState(actionBlock: CoroutineScope.(oldState: S) -> Unit) =
        with(viewModelScope) { actionBlock(_state.value) }

    override fun updateState(actionBlock: CoroutineScope.(oldState: S) -> S) {
        _state.update { with(viewModelScope) { actionBlock(_state.value) } }
    }
}