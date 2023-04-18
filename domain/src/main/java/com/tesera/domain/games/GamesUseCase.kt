package com.tesera.domain.games

import androidx.paging.PagingData
import com.tesera.domain.games.filters.GamesFilterRepository
import com.tesera.domain.model.GamePreviewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository,
    private val gamesFilterRepository: GamesFilterRepository,
) {
    fun getLatestHotnessGames(): Flow<List<GamePreviewModel>> =
        gamesRepository.getLatestGames(gamesFilterRepository.getFilter())

    fun getHotnessGames(): Flow<PagingData<GamePreviewModel>> =
        gamesRepository.getGames(gamesFilterRepository.getFilter())
}