package com.tesera.domain.games

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    fun getLastHotnessGames(): Flow<List<GamePreviewModel>> =
        gamesRepository.games(GamePageParams(type = "hotness", sort = "hotness"))

    fun getLastHotnessGames1(): Flow<PagingData<GamePreviewModel>> =
        gamesRepository.getGames(GamePageParams(type = "hotness", sort = "hotness"))
}