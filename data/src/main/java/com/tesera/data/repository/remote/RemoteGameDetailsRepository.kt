package com.tesera.data.repository.remote

import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.gameDetails.GameDetailsRepository
import com.tesera.domain.model.GameDetailsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteGameDetailsRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource,
) : GameDetailsRepository {
    override fun getGameDetails(alias: String): Flow<GameDetailsModel> = flow {
        datasource.getGameDetails(alias).onSuccess { emit(it.toModel()) }
    }.flowOn(ioDispatcher)

}