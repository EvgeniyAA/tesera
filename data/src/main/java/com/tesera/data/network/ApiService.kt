package com.tesera.data.network

import com.tesera.data.network.model.request.AuthParams
import com.tesera.data.network.model.request.WakeUpParams
import com.tesera.data.network.model.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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
    suspend fun news(
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int = 0,
    ): Result<List<NewsPreviewResponse>>

    @GET("/news/{alias}")
    suspend fun news(
        @Path("alias") id: String,
    ): Result<Any>

    // игры
    @GET("/games")
    suspend fun games(
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int = 0,
        @Query("Type") type: String,
        @Query("sort") sort: String,
    ): Result<List<GameResponse>>

    @GET("/games/{alias}")
    suspend fun gameDetails(@Path("alias") id: String): Result<GameDetailsResponse>

    @GET("/comments/{objecttype}/{alias}/{lastcommentid}")
    suspend fun comments(
        @Path("objecttype") objectType: String,
        @Path("alias") alias: String,
        @Path("lastcommentid") lastCommentId: Int,
        @Query("limit") limit: Int?,
    ): Result<List<CommentResponse>>

    @GET("/games/{alias}/links")
    suspend fun links(
        @Path("alias") alias: String,
        @Query("limit") limit: Int,
    ): List<LinkResponse>

    @GET("/games/{alias}/files")
    suspend fun files(
        @Path("alias") alias: String,
        @Query("limit") limit: Int,
    ): List<FileResponse>

    // продают
    @GET("/trade/sales/")
    suspend fun sales(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int?,
        @Query("alias") alias: String?
    ): List<Any>

    // покупают
    @GET("/trade/purchases/")
    suspend fun purchases(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int?,
        @Query("alias") alias: String?
    ): List<Any>

    @GET("/games/{alias}/owns/")
    suspend fun owns(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Path("alias") alias: String
    ): List<Any>

    @GET("/reports/")
    suspend fun reports(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("game") alias: String,
        @Query("AccessType") accessType: Int?
    ): List<Any>
}