package com.tesera.feature.gamedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tesera.core.constants.KEY_ALIAS
import com.tesera.core.mvi.MviViewModel
import com.tesera.core.mvi.Reducer
import com.tesera.domain.gameDetails.GameDetailsUseCase
import com.tesera.feature.gamedetails.models.GameDetailsIntent
import com.tesera.feature.gamedetails.models.GameDetailsViewState
import com.tesera.feature.gamedetails.models.mapToIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: GameDetailsUseCase,
) : MviViewModel<GameDetailsViewState, GameDetailsIntent>() {

    private val reducer = GameDetailsReducer(GameDetailsViewState())

    override val state: StateFlow<GameDetailsViewState>
        get() = reducer.state

    init {
        getGameDetails()
    }

    fun getGameDetails() {
        viewModelScope.launch {
            useCase.getGameDetails(savedStateHandle.get<String>(KEY_ALIAS) ?: "").collect {
                obtainIntent(it.mapToIntent())
            }
        }
    }

    fun onExpandClicked() {
        obtainIntent(GameDetailsIntent.UiActions.ExpandDescription)
    }

    override fun obtainIntent(intent: GameDetailsIntent) {
        reducer.sendIntent(intent)
    }

    private inner class GameDetailsReducer(initial: GameDetailsViewState) :
        Reducer<GameDetailsViewState, GameDetailsIntent>(initial) {
        override fun reduce(oldState: GameDetailsViewState, intent: GameDetailsIntent) {
            when (intent) {
                is GameDetailsIntent.PartialState.Error -> setState(
                    oldState.copy(
                        isLoading = false,
                        allGameInfo = null,
                        error = intent.error
                    )
                )

                GameDetailsIntent.PartialState.Loading -> setState(
                    oldState.copy(
                        isLoading = true,
                        allGameInfo = null,
                        error = null
                    )
                )

                is GameDetailsIntent.PartialState.Result -> setState(
                    oldState.copy(
                        isLoading = false,
                        allGameInfo = intent.gameDetails,
                        error = null
                    )
                )

                GameDetailsIntent.UiActions.ExpandDescription -> setState(
                    oldState.copy(
                        isDescriptionExpanded = true
                    )
                )
            }
        }
    }
}