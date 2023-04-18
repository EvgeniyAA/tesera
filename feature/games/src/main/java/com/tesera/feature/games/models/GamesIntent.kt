package com.tesera.feature.games.models

import com.tesera.domain.model.GamePreviewModel

sealed class GamesIntent {
    data class GameDetailsClicked(val game: GamePreviewModel) : GamesIntent()
    object ActionInvoked : GamesIntent()
}