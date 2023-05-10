package com.tesera.feature.home.models

import com.tesera.domain.model.GamePreviewModel
import com.tesera.domain.model.NewsPreviewModel

data class HomeViewState(
    val hotnessGames: List<GamePreviewModel> = emptyList(),
    val news: List<NewsPreviewModel> = emptyList(),
    val isLoading: Boolean = true,
    val action: HomeAction = HomeAction.None,
)

sealed class HomeAction {
    object ToGamesList : HomeAction()
    data class ToGameDetails(val game: GamePreviewModel) : HomeAction()
    object ToNewsList : HomeAction()
    data class ToNewsDetails(val news: NewsPreviewModel) : HomeAction()
    object None : HomeAction()
}