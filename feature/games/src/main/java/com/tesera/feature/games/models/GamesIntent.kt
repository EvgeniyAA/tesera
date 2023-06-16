package com.tesera.feature.games.models

sealed class GamesIntent {
    object ActionInvoked : GamesIntent()
}