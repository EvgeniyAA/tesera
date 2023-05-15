package com.tesera.domain.gameDetails

import com.tesera.domain.model.GameDetails

interface GameDetailsRepository {
    suspend fun getGameDetails(alias: String): GameDetails
}