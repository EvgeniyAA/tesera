package com.tesera.feature.gamedetails.models

import com.tesera.domain.model.*

data class GameDetailsViewState(
    val allGameInfo: GameDetailsModel? = null,
    val isLoading: Boolean = true,
    val action: GameDetailsAction = GameDetailsAction.None,
)

sealed class GameDetailsAction {
    object None : GameDetailsAction()
    data class ToGameDetails(val game: GamePreviewModel) : GameDetailsAction()
    data class ToNewsDetails(val news: NewsPreviewModel) : GameDetailsAction()
    data class ToComments(val game: GameDetailsModel) : GameDetailsAction()
    data class ToMedia(val game: GameDetailsModel) : GameDetailsAction()
}