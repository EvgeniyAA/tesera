package com.tesera.feature.games.models

import com.tesera.domain.model.GamePreview

sealed class GamesIntent {
    object ActionInvoked : GamesIntent()
}