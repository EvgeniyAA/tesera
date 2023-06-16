package com.tesera.feature.games.models

data class GamesViewState(
    val action: GamesAction = GamesAction.None
)

sealed class GamesAction {
    object None : GamesAction()
}