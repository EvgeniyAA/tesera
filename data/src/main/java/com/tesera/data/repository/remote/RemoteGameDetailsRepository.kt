package com.tesera.data.repository.remote

import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.gameDetails.GameDetailsRepository
import com.tesera.domain.model.GameDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteGameDetailsRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource,
) : GameDetailsRepository {
    override suspend fun getGameDetails(alias: String): GameDetails = withContext(ioDispatcher) {
        datasource.getGameDetails(alias).toModel()
    }
}