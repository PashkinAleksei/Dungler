package ru.lemonapes.dungler.navigation.character

import com.limonapes.dungle.parent_store.ViewModelStore
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob

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