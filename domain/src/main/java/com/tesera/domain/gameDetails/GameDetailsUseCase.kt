package com.tesera.domain.gameDetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameDetailsUseCase @Inject constructor(
    private val gameDetailsRepository: GameDetailsRepository,
) {
    fun getGameDetails(alias: String): Flow<GameDetailsPartialState> = flow {
        emit(GameDetailsPartialState.Loading)
        try {
            val result = gameDetailsRepository.getGameDetails(alias)
            emit(GameDetailsPartialState.Result(result))

        } catch (e: Exception) {
            emit(GameDetailsPartialState.Error(e))
        }
    }
}