package com.tesera.feature.gamedetails.models

import com.tesera.domain.model.GamePreviewModel

sealed class GameDetailsIntent {
    data class GetGameDetails(val alias: String) : GameDetailsIntent()
    data class GameDetailsClicked(val game: GamePreviewModel) : GameDetailsIntent()
    object ExpandCommentsClicked : GameDetailsIntent()
    object ActionInvoked : GameDetailsIntent()
}