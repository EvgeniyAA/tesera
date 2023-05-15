package com.tesera.feature.games.models

import com.tesera.domain.model.GamePreview

sealed class GamesIntent {
    data class GameDetailsClicked(val game: GamePreview) : GamesIntent()
    object ActionInvoked : GamesIntent()
}