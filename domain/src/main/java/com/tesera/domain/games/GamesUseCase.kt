package com.tesera.domain.games

import androidx.paging.PagingData
import com.tesera.domain.games.filters.GamesFilter
import com.tesera.domain.games.filters.GamesType
import com.tesera.domain.model.GamePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository,
) {
    suspend fun getLatestHotnessGames(): List<GamePreview> = try {
        gamesRepository.getLatestGames(GamesFilter(type = GamesType.HOTNESS))
    } catch (e: Exception) {
        emptyList()
    }

    fun getHotnessGames(): Flow<PagingData<GamePreview>> =
        gamesRepository.getGames(GamesFilter(type = GamesType.HOTNESS))
}