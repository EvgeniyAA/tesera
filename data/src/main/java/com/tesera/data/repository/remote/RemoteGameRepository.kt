package com.tesera.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.GameResponse
import com.tesera.data.network.model.response.toGamePreviewModel
import com.tesera.domain.games.GamePageParams
import com.tesera.domain.games.GamePreviewModel
import com.tesera.domain.games.GamesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteGameRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource
) : GamesRepository {
    override fun games(params: GamePageParams): Flow<List<GamePreviewModel>> = flow {
        datasource.getGames(params.limit, params.offset, params.type, params.sort)
            .onSuccess { games ->
                emit(games.map { it.toGamePreviewModel() })
            }
    }.flowOn(ioDispatcher)

    override fun getGames(params: GamePageParams): Flow<PagingData<GamePreviewModel>> = Pager(
        config = PagingConfig(pageSize = 15),
        pagingSourceFactory = {
            GamePagingSource(datasource, params)
        }
    ).flow
}