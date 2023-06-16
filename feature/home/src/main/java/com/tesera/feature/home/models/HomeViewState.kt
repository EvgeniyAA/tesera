package com.tesera.feature.home.models

import androidx.compose.runtime.Stable
import com.tesera.core.mvi.UiState
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
    val action: HomeAction = HomeAction.None
) : UiState

@Stable
sealed class HomeAction {
    object None: HomeAction()
}