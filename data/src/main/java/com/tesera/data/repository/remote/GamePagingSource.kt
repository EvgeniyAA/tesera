package com.tesera.data.repository.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.model.response.toGamePreviewModel
import com.tesera.domain.model.GamePreview
import com.tesera.domain.games.filters.GamesFilter

class GamePagingSource(
    private val datasource: NetworkDataSource,
    private val pageParams: GamesFilter,
) : PagingSource<Int, GamePreview>() {
    override fun getRefreshKey(state: PagingState<Int, GamePreview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamePreview> {
        return try {
            val page = params.key ?: 0

            val games = datasource.getGames(
                pageParams.limit,
                page,
                pageParams.type.value,
                pageParams.sort.value
            )
//                return LoadResult.Error(it)

            return LoadResult.Page(
                data = games.map { it.toGamePreviewModel() },
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (games.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}