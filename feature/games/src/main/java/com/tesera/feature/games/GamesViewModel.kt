package com.tesera.feature.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tesera.core.mvi.IntentHandler
import com.tesera.domain.games.GamesUseCase
import com.tesera.domain.model.GamePreview
import com.tesera.feature.games.models.GamesAction
import com.tesera.feature.games.models.GamesIntent
import com.tesera.feature.games.models.GamesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val gamesUseCase: GamesUseCase,
) : ViewModel(), IntentHandler<GamesIntent> {

    private val _gamesViewState: MutableStateFlow<GamesViewState> =
        MutableStateFlow(GamesViewState())
    val gamesViewState: StateFlow<GamesViewState> = _gamesViewState

    fun getGames(): Flow<PagingData<GamePreview>> =
        gamesUseCase.getHotnessGames().cachedIn(viewModelScope)

    override fun obtainIntent(intent: GamesIntent) = when (intent) {
        GamesIntent.ActionInvoked ->
            sendViewState(_gamesViewState.value.copy(action = GamesAction.None))
        is GamesIntent.GameDetailsClicked -> sendViewState(
            _gamesViewState.value.copy(action = GamesAction.ToGameDetails(intent.game))
        )
    }

    private fun sendViewState(viewState: GamesViewState) {
        viewModelScope.launch {
            _gamesViewState.emit(viewState)
        }
    }
}