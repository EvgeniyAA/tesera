package com.tesera.domain.home

import com.tesera.domain.model.GamePreview
import com.tesera.domain.model.NewsPreview

sealed class HomePartialState {
    data class Games(val games: List<GamePreview> = emptyList()) : HomePartialState()
    object GamesLoading : HomePartialState()
    data class GamesLoadingError(val error: Throwable?) : HomePartialState()

    data class News(val news: List<NewsPreview> = emptyList()) : HomePartialState()
    object NewsLoading : HomePartialState()
    data class NewsLoadingError(val error: Throwable?) : HomePartialState()

    fun partial() = this
}