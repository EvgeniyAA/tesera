package com.tesera.feature.home.models

import com.tesera.domain.model.GamePreviewModel

sealed class HomeIntent {
    data class GameDetailsClicked(val game: GamePreviewModel) : HomeIntent()
    object GameListClicked : HomeIntent()
    object NewsListClicked : HomeIntent()
    object ActionInvoked : HomeIntent()
}