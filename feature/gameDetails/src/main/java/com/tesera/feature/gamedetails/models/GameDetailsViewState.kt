package com.tesera.feature.gamedetails.models

import com.tesera.domain.model.GameDetailsModel
import com.tesera.domain.model.GamePreviewModel

data class GameDetailsViewState(
    val allGameInfo: GameDetailsModel? = null,
    val isLoading: Boolean = true,
    val action: GameDetailsAction = GameDetailsAction.None,
)

sealed class GameDetailsAction {
    object None : GameDetailsAction()
    data class ToGameDetails(val game: GamePreviewModel) : GameDetailsAction()
    object ExpandComments : GameDetailsAction()
}