package com.tesera.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.news.NewsPageParams
import com.tesera.domain.news.NewsPreviewModel
import com.tesera.domain.news.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteNewsRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource
) : NewsRepository {

    override fun getLatestNews(params: NewsPageParams): Flow<List<NewsPreviewModel>> = flow {
        datasource.getNews(params.limit, params.offset)
            .onSuccess { news ->
                emit(news.map { it.toModel() })
            }
    }.flowOn(ioDispatcher)

    override fun getNews(params: NewsPageParams): Flow<PagingData<NewsPreviewModel>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = { NewsPagingSource(datasource, params) }
    ).flow
}