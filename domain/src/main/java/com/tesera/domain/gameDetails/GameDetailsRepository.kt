package com.tesera.domain.gameDetails

import com.tesera.domain.model.GameDetailsModel
import kotlinx.coroutines.flow.Flow

interface GameDetailsRepository {
    fun getGameDetails(alias: String): Flow<GameDetailsModel>
}