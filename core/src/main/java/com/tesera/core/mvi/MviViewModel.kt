package com.tesera.core.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class MviViewModel<STATE : UiState, INTENT: UiIntent> : ViewModel(), IntentHandler<INTENT> {
    abstract val state: StateFlow<STATE>
}