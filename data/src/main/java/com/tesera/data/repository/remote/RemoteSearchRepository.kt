package com.tesera.data.repository.remote

import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toModel
import com.tesera.data.storage.TeseraPrefs
import com.tesera.domain.model.SearchItem
import com.tesera.domain.search.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoteSearchRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val teseraPrefs: TeseraPrefs,
    private val datasource: NetworkDataSource,
) : SearchRepository {
    override suspend fun searchGames(query: String): List<SearchItem> {
        teseraPrefs.searchHistory = setOf(query)
        return datasource.searchGames(query).map { it.toModel() }
    }

    override suspend fun searchHistory(): List<String> = teseraPrefs.searchHistory.toList()
}