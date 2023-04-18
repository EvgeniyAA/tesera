package com.tesera.domain.gameDetails

import javax.inject.Inject

class GameDetailsUseCase @Inject constructor(
    private val gameDetailsRepository: GameDetailsRepository,
) {
    fun getGameDetails(alias: String) = gameDetailsRepository.getGameDetails(alias)
}