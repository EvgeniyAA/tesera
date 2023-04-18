package com.tesera.domain.games

import androidx.paging.PagingData
import com.tesera.domain.games.filters.GamesFilter
import com.tesera.domain.model.GamePreviewModel
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getLatestGames(params: GamesFilter): Flow<List<GamePreviewModel>>
    fun getGames(params: GamesFilter): Flow<PagingData<GamePreviewModel>>
}