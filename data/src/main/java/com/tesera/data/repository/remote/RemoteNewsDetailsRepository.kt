package com.tesera.data.repository.remote

import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.model.NewsInfo
import com.tesera.domain.news.NewsDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteNewsDetailsRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource
) : NewsDetailsRepository{
    override suspend fun getNews(alias: String): NewsInfo = withContext(ioDispatcher) {
        datasource.getNews(alias).toModel()
    }

    override suspend fun getThoughts(alias: String): NewsInfo = withContext(ioDispatcher) {
        datasource.getThoughts(alias).toModel()
    }

    override suspend fun getArticles(alias: String): NewsInfo = withContext(ioDispatcher) {
        datasource.getArticles(alias).toModel()
    }

    override suspend fun getJournals(alias: String): NewsInfo = withContext(ioDispatcher) {
        datasource.getJournals(alias).toModel()
    }
}