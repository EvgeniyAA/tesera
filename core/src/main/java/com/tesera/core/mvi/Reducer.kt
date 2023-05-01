package com.tesera.core.mvi

import com.tesera.core.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<STATE: UiState, INTENT: UiIntent>(initialState: STATE) {
    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val state: StateFlow<STATE>
    get() = _state

    val timeCapsule: TimeCapsule<STATE> = TimeTravelCapsule { storedState ->
        _state.tryEmit(storedState)
    }

    init {
        timeCapsule.addState(initialState)
    }

    fun sendIntent(intent: INTENT) {
        reduce(_state.value, intent)
    }

    fun setState(newState: STATE){
        val success = _state.tryEmit(newState)

        if(BuildConfig.DEBUG && success)
            timeCapsule.addState(newState)
    }

    abstract fun reduce(oldState: STATE, intent: INTENT)
}

interface UiState
interface UiIntent