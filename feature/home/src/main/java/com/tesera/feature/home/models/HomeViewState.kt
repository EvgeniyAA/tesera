package com.tesera.feature.home.models

import com.tesera.domain.games.GamePreviewModel
import com.tesera.domain.news.NewsPreviewModel

data class HomeViewState(
    val hotnessGames: List<GamePreviewModel> = emptyList(),
    val news: List<NewsPreviewModel> = emptyList(),
    val isLoading: Boolean = true,
    val action: HomeAction = HomeAction.None
)

sealed class HomeAction {
    object ToGamesList : HomeAction()
    data class ToGameDetails(val id: Int) : HomeAction()
    object ToNewsList : HomeAction()
    object None : HomeAction()
}