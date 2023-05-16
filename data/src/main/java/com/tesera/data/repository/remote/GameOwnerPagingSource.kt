package com.tesera.data.repository.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.model.GameOwner
import com.tesera.domain.users.UsersPageParams

class GameOwnerPagingSource(
    private val datasource: NetworkDataSource,
    private val pageParams: UsersPageParams,
) : PagingSource<Int, GameOwner>() {
    override fun getRefreshKey(state: PagingState<Int, GameOwner>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameOwner> {
        return try {
            val page = params.key ?: 0

            val owners =
                datasource.getOwners(pageParams.alias, pageParams.limit, page)

//                        return LoadResult.Error(it)

            return LoadResult.Page(
                data = owners.map { it.toModel() },
                prevKey = null,
                nextKey = if (owners.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}