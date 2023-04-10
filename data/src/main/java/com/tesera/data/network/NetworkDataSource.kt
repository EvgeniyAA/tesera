package com.tesera.data.network

import com.tesera.data.network.model.request.AuthParams
import com.tesera.data.network.model.request.WakeUpParams
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val api: ApiService
) {
    suspend fun login(username: String, password: String) =
        api.login(AuthParams(username, password))

    suspend fun authInfo() = api.authInfo()

    suspend fun getGames(limit: Int, offset: Int, type: String, sort: String) =
        api.games(limit, offset, type, sort)

    suspend fun getNews(limit: Int, offset: Int) =
        api.news(limit, offset)
}