package com.tesera.data.network

import com.tesera.data.network.model.request.AuthParams
import com.tesera.data.network.model.request.WakeUpParams
import com.tesera.data.network.model.response.AuthResponse
import com.tesera.data.network.model.response.GameResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body params: AuthParams): Result<AuthResponse>

    @GET("auth/info")
    suspend fun authInfo(): Result<Any>

    // статьи и списки
    @GET("/articles")
    suspend fun articles(): Result<Any>

    // новости
    @GET("/news")
    suspend fun news(): Result<Any>

    // игры
    @GET("/games")
    suspend fun games(
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int = 0,
        @Query("Type") type: String,
        @Query("sort") sort: String
    ): Result<List<GameResponse>>
}