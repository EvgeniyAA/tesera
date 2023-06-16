package com.tesera.feature.gamedetails.models

import androidx.compose.runtime.Stable
import com.tesera.core.mvi.UiState
import com.tesera.domain.model.GameDetails

@Stable
data class GameDetailsViewState(
    val allGameInfo: GameDetails? = null,
    val isDescriptionExpanded: Boolean = false,
    val isLoading: Boolean = true,
    val error: Throwable? = null
) : UiState
