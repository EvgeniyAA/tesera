package com.tesera.data.network

import com.tesera.data.network.model.request.AuthParams
import com.tesera.data.network.model.response.*
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body params: AuthParams): Result<AuthResponse>

    @GET("auth/info")
    suspend fun authInfo(): Result<Any>

    // новости
    @GET("/news")
    suspend fun news(
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int = 0,
    ): List<NewsPreviewResponse>

    @GET("/publications")
    suspend fun publications(
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int = 0,
    ): List<NewsPreviewResponse>

    // новости
    @GET("/news/{alias}")
    suspend fun news(
        @Path("alias") alias: String,
    ): NewsInfoResponse

    // мысли
    @GET("/thoughts/{alias}")
    suspend fun thoughts(
        @Path("alias") alias: String,
    ): ThoughtResponse

    // статьи
    @GET("/articles/{alias}")
    suspend fun articles(
        @Path("alias") alias: String,
    ): ArticleResponse

    // отчеты
    @GET("/journals/{alias}")
    suspend fun journals(
        @Path("alias") alias: String,
    ): JournalResponse

    // игры
    @GET("/games")
    suspend fun games(
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int = 0,
        @Query("Type") type: String,
        @Query("sort") sort: String,
    ): List<GameResponse>

    @GET("/search/games")
    suspend fun searchGames(
        @Query("query") query: String,
        @Query("limit") limit: Int = 10,
    ): List<SearchItemResponse>

    @GET("/games/{alias}")
    suspend fun gameDetails(@Path("alias") id: String): GameDetailsResponse

    @GET("/comments/{objecttype}/{alias}/{lastcommentid}")
    suspend fun comments(
        @Path("objecttype") objectType: String,
        @Path("alias") alias: String,
        @Path("lastcommentid") lastCommentId: Int,
        @Query("limit") limit: Int?,
    ): List<CommentResponse>

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

    @GET("/games/{alias}/owns")
    suspend fun owns(
        @Path("alias") alias: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): List<GameOwnerResponse>

    // продают
    @GET("/trade/sales/")
    suspend fun sales(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int?,
        @Query("game") alias: String?,
        @Query("user") user: String?,
    ): List<MarketItemResponse>

    // покупают
    @GET("/trade/purchases/")
    suspend fun purchases(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int?,
        @Query("game") alias: String?,
        @Query("user") user: String?,
    ): List<MarketItemResponse>

    @GET("/reports/")
    suspend fun reports(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("game") alias: String,
        @Query("AccessType") accessType: Int?,
    ): List<Any>

    @GET("/user/{username}")
    suspend fun profile(
        @Path("username") username: String
    ): ProfileResponse

    @GET("/collections/user/{username}")
    suspend fun userCollections(
        @Path("username") username: String
    ): CollectionsResponse

    @GET("/reports/user/{username}/info")
    suspend fun userReports(
        @Path("username") username: String
    ): UserReportsInfoResponse
}