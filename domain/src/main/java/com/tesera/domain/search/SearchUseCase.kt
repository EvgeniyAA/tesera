package com.tesera.domain.search

import com.tesera.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    private val queryFlow = MutableSharedFlow<String>()

    suspend fun search(query: String) {
        queryFlow.emit(query)
    }

    suspend fun observeSearchResults(): Flow<SearchPartialState> = merge(queryFlow
        .debounce(500)
        .distinctUntilChanged()
        .mapLatest { SearchPartialState.SearchResult(performSearch(it.lowercase())).partial() },
        flowOf(searchRepository.searchHistory()).map {
            SearchPartialState.SearchHistory(it).partial()
        })

    private suspend fun performSearch(query: String): List<SearchItem> =
        searchRepository.searchGames(query)

}