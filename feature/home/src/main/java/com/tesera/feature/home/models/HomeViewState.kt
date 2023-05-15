package com.tesera.feature.home.models

import androidx.compose.runtime.Stable
import com.tesera.domain.model.GamePreview
import com.tesera.domain.model.NewsPreview

@Stable
data class HomeViewState(
    val hotnessGames: List<GamePreview> = emptyList(),
    val news: List<NewsPreview> = emptyList(),
    val isGamesLoading: Boolean = true,
    val isNewsLoading: Boolean = true,
    val newsLoadingError: Throwable? = null,
    val gamesLoadingError: Throwable? = null,
)