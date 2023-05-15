package com.tesera.feature.gamedetails.models

import androidx.compose.runtime.Stable
import com.tesera.domain.model.GameDetails

@Stable
data class GameDetailsViewState(
    val allGameInfo: GameDetails? = null,
    val isLoading: Boolean = true,
    val error: Throwable? = null
)
