package com.tesera.domain.games

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getLatestGames(params: GamePageParams): Flow<List<GamePreviewModel>>
    fun getGames(params: GamePageParams): Flow<PagingData<GamePreviewModel>>
}