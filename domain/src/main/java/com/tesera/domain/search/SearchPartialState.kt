package com.tesera.domain.search

import com.tesera.domain.model.SearchItem

sealed class SearchPartialState {
    data class SearchHistory(val history: List<String>) : SearchPartialState()
    data class SearchResult(val result: List<SearchItem>) : SearchPartialState()

    fun partial() = this
}