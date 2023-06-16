package com.tesera.feature.games.models

import com.tesera.domain.model.GamePreview

data class GamesViewState(
    val action: GamesAction = GamesAction.None
)

sealed class GamesAction {
    object None : GamesAction()
}