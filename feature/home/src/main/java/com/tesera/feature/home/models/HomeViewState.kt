package com.tesera.feature.home.models

import com.tesera.domain.games.GamePreviewModel

sealed class HomeViewState {
    object Loading : HomeViewState()
    data class Content(val hotnessGames: List<GamePreviewModel>) : HomeViewState()
}