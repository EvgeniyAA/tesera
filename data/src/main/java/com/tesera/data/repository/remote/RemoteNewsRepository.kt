package com.tesera.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.news.NewsPageParams
import com.tesera.domain.model.NewsPreview
import com.tesera.domain.news.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteNewsRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource
) : NewsRepository {

    override suspend fun getLatestNews(params: NewsPageParams): List<NewsPreview> = withContext(ioDispatcher) {
        datasource.getNews(params.limit, params.offset)
            .map { it.toModel() }
            .onEach { println(it.toString()) }
    }

    override fun getNews(params: NewsPageParams): Flow<PagingData<NewsPreview>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { NewsPagingSource(datasource, params) }
    ).flow
}