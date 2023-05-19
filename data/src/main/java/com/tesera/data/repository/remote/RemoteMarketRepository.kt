package com.tesera.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.domain.market.MarketGameItemPageParams
import com.tesera.domain.market.MarketRepository
import com.tesera.domain.model.MarketGameItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteMarketRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource,
) : MarketRepository {
    override fun getMarketItems(params: MarketGameItemPageParams): Flow<PagingData<MarketGameItem>> =
        Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { MarketGameItemPagingSource(datasource, params) }
        ).flow
}