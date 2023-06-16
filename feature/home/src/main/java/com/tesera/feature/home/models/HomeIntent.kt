package com.tesera.feature.home.models

import com.tesera.core.mvi.UiIntent
import com.tesera.domain.home.HomePartialState
import com.tesera.domain.model.GamePreview
import com.tesera.domain.model.NewsPreview

sealed class HomeIntent : UiIntent {
    sealed class UiActions : HomeIntent() {
    }

    sealed class PartialState: HomeIntent() {
        data class Games(val games: List<GamePreview> = emptyList()) : PartialState()
        object GamesLoading : PartialState()
        data class GamesLoadingError(val error: Throwable?) : PartialState()

        data class News(val news: List<NewsPreview> = emptyList()) : PartialState()
        object NewsLoading : PartialState()
        data class NewsLoadingError(val error: Throwable?) : PartialState()
    }
}

fun HomePartialState.mapToIntent() = when(this) {
    is HomePartialState.Games -> HomeIntent.PartialState.Games(this.games)
    HomePartialState.GamesLoading -> HomeIntent.PartialState.GamesLoading
    is HomePartialState.GamesLoadingError -> HomeIntent.PartialState.GamesLoadingError(this.error)
    is HomePartialState.News -> HomeIntent.PartialState.News(this.news)
    HomePartialState.NewsLoading -> HomeIntent.PartialState.NewsLoading
    is HomePartialState.NewsLoadingError -> HomeIntent.PartialState.NewsLoadingError(this.error)
}