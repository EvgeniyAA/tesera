package com.tesera.data.network

import com.tesera.data.network.model.request.AuthParams
import com.tesera.domain.model.GameFile
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

const val SEGMENT_SIZE = 8192 // or any other value that you need

class NetworkDataSource @Inject constructor(
    private val api: ApiService,
) {
    suspend fun login(username: String, password: String) =
        api.login(AuthParams(username, password))

    suspend fun authInfo() = api.authInfo()

    suspend fun getGames(limit: Int, offset: Int, type: String, sort: String) =
        api.games(limit, offset, type, sort)

    suspend fun getGameDetails(alias: String) = api.gameDetails(alias)

    suspend fun getNews(limit: Int, offset: Int) =
        api.publications(limit, offset)

    suspend fun getComments(
        objecttype: String,
        alias: String,
        lastCommentId: Int,
        limit: Int?,
    ) = api.comments(objecttype, alias, lastCommentId, limit)

    suspend fun getLinks(alias: String, limit: Int) = api.links(alias, limit)
    suspend fun getFiles(alias: String, limit: Int) = api.files(alias, limit)

    suspend fun getOwners(alias: String, limit: Int, offset: Int) = api.owns(alias, limit, offset)

    suspend fun purchases(limit: Int, offset: Int, alias: String? = null, user: String? = null) =
        api.purchases(limit, offset, alias, user)

    suspend fun sales(limit: Int, offset: Int, alias: String? = null, user: String? = null) =
        api.sales(limit, offset, alias, user)

    fun downloadFile(gameFile: GameFile): Response {
        val request = Request.Builder().url(gameFile.photoUrl).build()
        return OkHttpClient().newCall(request).execute()
    }

    suspend fun getNews(alias: String) = api.news(alias)
    suspend fun getThoughts(alias: String) = api.thoughts(alias)
    suspend fun getArticles(alias: String) = api.articles(alias)
    suspend fun getJournals(alias: String) = api.journals(alias)
}