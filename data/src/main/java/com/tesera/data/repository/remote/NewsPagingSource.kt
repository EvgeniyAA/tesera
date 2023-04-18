package com.tesera.data.repository.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.news.NewsPageParams
import com.tesera.domain.model.NewsPreviewModel

class NewsPagingSource(
    private val datasource: NetworkDataSource,
    private val pageParams: NewsPageParams
) : PagingSource<Int, NewsPreviewModel>() {
    override fun getRefreshKey(state: PagingState<Int, NewsPreviewModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsPreviewModel> {
        return try {
            val page = params.key ?: 0

            val news =
                datasource.getNews(pageParams.limit, page)
                    .getOrElse {
                        return LoadResult.Error(it)
                    }
            return LoadResult.Page(
                data = news.map { it.toModel() },
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (news.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}