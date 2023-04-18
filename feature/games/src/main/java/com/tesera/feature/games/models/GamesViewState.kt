package com.tesera.feature.games.models

import com.tesera.domain.model.GamePreviewModel

data class GamesViewState(
    val action: GamesAction = GamesAction.None
)

sealed class GamesAction {
    object None : GamesAction()
    data class ToGameDetails(val game: GamePreviewModel) : GamesAction()
}