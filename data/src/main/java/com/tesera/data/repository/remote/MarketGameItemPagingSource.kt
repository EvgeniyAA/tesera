package com.tesera.data.repository.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.market.MarketGameItemPageParams
import com.tesera.domain.market.MarketType
import com.tesera.domain.model.MarketGameItem

class MarketGameItemPagingSource(
    private val datasource: NetworkDataSource,
    private val pageParams: MarketGameItemPageParams,
) : PagingSource<Int, MarketGameItem>() {
    override fun getRefreshKey(state: PagingState<Int, MarketGameItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarketGameItem> {
        return try {
            val page = params.key ?: 0

            val result = when (pageParams.type) {
                MarketType.Sell -> datasource.sales(
                    pageParams.limit,
                    page,
                    pageParams.alias,
                    pageParams.user
                )

                MarketType.Buy -> datasource.purchases(
                    pageParams.limit,
                    page,
                    pageParams.alias,
                    pageParams.user
                )
            }

//                        return LoadResult.Error(it)

            return LoadResult.Page(
                data = result.map { it.toModel() },
                prevKey = null,
                nextKey = if (result.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
