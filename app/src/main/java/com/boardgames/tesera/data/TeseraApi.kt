package com.boardgames.tesera.data

import com.boardgames.tesera.data.model.GameType
import com.boardgames.tesera.data.model.SortType
import com.boardgames.tesera.data.response.GameInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TeseraApi {

    @GET("/games")
    suspend fun getGames(
        @Query("type") type: GameType?,
        @Query("sort") sort: SortType?,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<GameInfoResponse>
}