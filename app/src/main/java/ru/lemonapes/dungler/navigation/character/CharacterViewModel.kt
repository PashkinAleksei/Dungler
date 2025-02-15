package ru.lemonapes.dungler.navigation.character

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.lemonapes.dungler.mappers.EquipmentResponseMapper
import ru.lemonapes.dungler.network.endpoints.loadEquipment
import ru.lemonapes.dungler.parent_store.ViewModelStore

interface CharAction {
    fun actionStart()
    fun actionError(throwable: Throwable)
    fun actionClearError()
}

class CharacterViewModel() :
    ViewModelStore<CharacterViewState>(CharacterViewState.EMPTY), CharAction {

    override val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        actionError(throwable)
    }

    init {
        actionStart()
    }

    override fun actionStart() = withActualState {
        launch(Dispatchers.IO + ceh) {
            val (gears, stats) = EquipmentResponseMapper.map(loadEquipment())

            updateState { state ->
                state.copy(gears = gears, stats = stats)
            }
        }
    }

    override fun actionError(throwable: Throwable) = updateState { oldState ->
        throwable.printStackTrace()
        oldState.copy(error = throwable)
    }

    override fun actionClearError() = updateState { oldState ->
        oldState.copy(error = null)
    }
}