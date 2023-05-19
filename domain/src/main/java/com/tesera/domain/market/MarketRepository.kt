package com.tesera.domain.market

import androidx.paging.PagingData
import com.tesera.domain.model.MarketGameItem
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    fun getMarketItems(params: MarketGameItemPageParams): Flow<PagingData<MarketGameItem>>
}