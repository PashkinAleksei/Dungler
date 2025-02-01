package ru.lemonapes.dungler.navigation.character

import ru.lemonapes.dungler.parent_store.ViewModelStore
import kotlinx.coroutines.CoroutineExceptionHandler

interface CharAction {
    fun actionStart()
    fun actionError(throwable: Throwable)
    fun actionClearError()
}

class CharacterViewModel() :
    ViewModelStore<CharacterViewState>(CharacterViewState.getEmpty()), CharAction {

    override val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        actionError(throwable)
    }

    init {
        actionStart()
    }

    override fun actionStart() = withActualState {

    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        oldState.copy(error = throwable)
    }

    override fun actionClearError() = updateState { oldState ->
        oldState.copy(error = null)
    }
}