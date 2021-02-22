package com.boardgames.tesera.data.repository

import com.boardgames.tesera.data.TeseraApi
import com.boardgames.tesera.data.model.GameType
import com.boardgames.tesera.data.model.SortType
import com.boardgames.tesera.data.response.toModel
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: TeseraApi
) {
    suspend fun getGames(
        offset: Int = 0,
        limit: Int = 20,
        sortType: SortType? = null,
        gameType: GameType? = null
        ) = api.getGames(gameType, sortType, offset, limit).map { it.toModel() }
}