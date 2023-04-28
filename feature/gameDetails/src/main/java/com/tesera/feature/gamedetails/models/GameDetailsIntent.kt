package com.tesera.feature.gamedetails.models

import com.tesera.designsystem.theme.components.GameDetailsButtonType
import com.tesera.domain.model.GameDetailsModel
import com.tesera.domain.model.GamePreviewModel

sealed class GameDetailsIntent {
    data class GetGameDetails(val alias: String) : GameDetailsIntent()
    data class GameDetailsClicked(val game: GamePreviewModel) : GameDetailsIntent()
    data class GameDetailsButtonClicked(
        val game: GameDetailsModel,
        val type: GameDetailsButtonType,
    ) : GameDetailsIntent()

    object ExpandCommentsClicked : GameDetailsIntent()
    object ActionInvoked : GameDetailsIntent()
}