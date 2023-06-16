package com.tesera.domain.gameDetails

import com.tesera.domain.model.GameDetails

sealed class GameDetailsPartialState {
    object Loading : GameDetailsPartialState()
    data class Result(val gameDetails: GameDetails) : GameDetailsPartialState()
    data class Error(val error: Throwable) : GameDetailsPartialState()
}