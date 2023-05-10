package com.tesera.feature.home.models

import com.tesera.domain.model.GamePreviewModel
import com.tesera.domain.model.NewsPreviewModel

sealed class HomeIntent {
    data class GameDetailsClicked(val game: GamePreviewModel) : HomeIntent()
    object GameListClicked : HomeIntent()
    object NewsListClicked : HomeIntent()
    data class NewsDetailsClicked(val news: NewsPreviewModel) : HomeIntent()
    object ActionInvoked : HomeIntent()
}