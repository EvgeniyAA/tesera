package com.tesera.domain.games

import androidx.paging.PagingData
import com.tesera.domain.games.filters.GamesFilter
import com.tesera.domain.model.GamePreview
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getLatestGames(params: GamesFilter): List<GamePreview>
    fun getGames(params: GamesFilter): Flow<PagingData<GamePreview>>
}