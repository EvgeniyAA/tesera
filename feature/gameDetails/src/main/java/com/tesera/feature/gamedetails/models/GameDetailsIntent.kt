package com.tesera.feature.gamedetails.models

import com.tesera.core.mvi.UiIntent
import com.tesera.domain.gameDetails.GameDetailsPartialState
import com.tesera.domain.model.GameDetails

sealed class GameDetailsIntent : UiIntent {
    sealed class UiActions : GameDetailsIntent() {
        object ExpandDescription: UiActions()
    }

    sealed class PartialState : GameDetailsIntent() {
        object Loading : PartialState()
        data class Result(val gameDetails: GameDetails) : PartialState()
        data class Error(val error: Throwable) : PartialState()
    }
}

fun GameDetailsPartialState.mapToIntent() = when (this) {
    is GameDetailsPartialState.Error -> GameDetailsIntent.PartialState.Error(this.error)
    GameDetailsPartialState.Loading -> GameDetailsIntent.PartialState.Loading
    is GameDetailsPartialState.Result -> GameDetailsIntent.PartialState.Result(this.gameDetails)
}