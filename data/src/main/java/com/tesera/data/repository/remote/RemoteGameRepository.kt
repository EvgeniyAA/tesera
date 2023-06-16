package com.tesera.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toGamePreviewModel
import com.tesera.domain.games.GamesRepository
import com.tesera.domain.games.filters.GamesFilter
import com.tesera.domain.model.GamePreview
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RemoteGameRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource,
) : GamesRepository {
    override suspend fun getLatestGames(params: GamesFilter): List<GamePreview> =
        withContext(ioDispatcher) {
            datasource.getGames(params.limit, params.offset, params.type.value, params.sort.value)
                .onEach { if (it.id == null || it.id == 0) Timber.e("Danger! $it \nbggId is incorrect") }
                .map { it.toGamePreviewModel() }

        }

    override fun getGames(params: GamesFilter): Flow<PagingData<GamePreview>> = Pager(
        config = PagingConfig(pageSize = 15),
        pagingSourceFactory = {
            GamePagingSource(datasource, params)
        }
    ).flow
}