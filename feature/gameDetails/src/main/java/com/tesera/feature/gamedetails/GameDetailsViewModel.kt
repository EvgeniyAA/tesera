package com.tesera.feature.gamedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesera.core.constants.KEY_ALIAS
import com.tesera.domain.gameDetails.GameDetailsUseCase
import com.tesera.feature.gamedetails.models.GameDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: GameDetailsUseCase,
) : ViewModel() {

    private val _gameDetailsViewState: MutableStateFlow<GameDetailsViewState> =
        MutableStateFlow(GameDetailsViewState())

    val gameDetailsViewState: StateFlow<GameDetailsViewState> = _gameDetailsViewState

    init {
        getGameDetails()
    }

    fun getGameDetails() {
        viewModelScope.launch {
            sendViewState(
                _gameDetailsViewState.value.copy(
                    isLoading = true,
                    allGameInfo = null,
                    error = null
                )
            )
            try {
                val result = useCase.getGameDetails(savedStateHandle.get<String>(KEY_ALIAS) ?: "")
                sendViewState(
                    _gameDetailsViewState.value.copy(
                        isLoading = false,
                        allGameInfo = result,
                        error = null
                    )
                )
            } catch (e: Exception) {
                sendViewState(
                    _gameDetailsViewState.value.copy(
                        isLoading = false,
                        allGameInfo = null,
                        error = e
                    )
                )
            }
        }
    }

    private fun sendViewState(viewState: GameDetailsViewState) {
        viewModelScope.launch {
            _gameDetailsViewState.emit(viewState)
        }
    }
}