package com.tesera.domain.games

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    fun getLatestHotnessGames(): Flow<List<GamePreviewModel>> =
        gamesRepository.getLatestGames(GamePageParams(limit = 15, type = "hotness", sort = "hotness"))

    fun getHotnessGames(): Flow<PagingData<GamePreviewModel>> =
        gamesRepository.getGames(GamePageParams(type = "hotness", sort = "hotness"))
}