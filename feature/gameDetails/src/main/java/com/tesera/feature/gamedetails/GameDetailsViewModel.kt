package com.tesera.feature.gamedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesera.core.mvi.IntentHandler
import com.tesera.designsystem.theme.components.GameDetailsButtonType
import com.tesera.domain.gameDetails.GameDetailsUseCase
import com.tesera.feature.gamedetails.models.GameDetailsAction
import com.tesera.feature.gamedetails.models.GameDetailsIntent
import com.tesera.feature.gamedetails.models.GameDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val gameDetailsUseCase: GameDetailsUseCase,
) : ViewModel(), IntentHandler<GameDetailsIntent> {

    private val _gameDetailsViewState: MutableStateFlow<GameDetailsViewState> =
        MutableStateFlow(GameDetailsViewState())

    val gameDetailsViewState: StateFlow<GameDetailsViewState> = _gameDetailsViewState

    override fun obtainIntent(intent: GameDetailsIntent) = when (intent) {
        GameDetailsIntent.ExpandCommentsClicked -> Unit
        is GameDetailsIntent.GetGameDetails -> getGameDetails(intent.alias)
        GameDetailsIntent.ActionInvoked ->
            sendViewState(_gameDetailsViewState.value.copy(action = GameDetailsAction.None))
        is GameDetailsIntent.GameDetailsClicked -> sendViewState(
            _gameDetailsViewState.value.copy(action = GameDetailsAction.ToGameDetails(intent.game))
        )
        is GameDetailsIntent.GameDetailsButtonClicked -> {
            val action = when (intent.type) {
                GameDetailsButtonType.Media -> GameDetailsAction.ToMedia(intent.game)
                GameDetailsButtonType.Comments -> GameDetailsAction.ToComments(intent.game)
                GameDetailsButtonType.HasGame -> GameDetailsAction.ToMedia(intent.game)
                GameDetailsButtonType.Sell -> GameDetailsAction.ToMedia(intent.game)
                GameDetailsButtonType.Buy -> GameDetailsAction.ToMedia(intent.game)
                GameDetailsButtonType.GameReports -> GameDetailsAction.ToMedia(intent.game)
            }

            sendViewState(_gameDetailsViewState.value.copy(action = action))
        }
    }

    private fun getGameDetails(alias: String) {
        viewModelScope.launch {
            sendViewState(_gameDetailsViewState.value.copy(isLoading = true))
            gameDetailsUseCase.getGameDetails(alias).collect {
                sendViewState(_gameDetailsViewState.value.copy(isLoading = false, allGameInfo = it))
            }
        }
    }

    private fun sendViewState(viewState: GameDetailsViewState) {
        viewModelScope.launch {
            _gameDetailsViewState.emit(viewState)
        }
    }
}