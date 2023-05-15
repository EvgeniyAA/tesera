package com.tesera.domain.gameDetails

import com.tesera.domain.model.GameDetails
import javax.inject.Inject

class GameDetailsUseCase @Inject constructor(
    private val gameDetailsRepository: GameDetailsRepository,
) {
    suspend fun getGameDetails(alias: String): GameDetails =
        gameDetailsRepository.getGameDetails(alias)
}