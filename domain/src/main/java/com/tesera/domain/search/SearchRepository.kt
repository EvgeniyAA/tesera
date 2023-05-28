package com.tesera.domain.search

import com.tesera.domain.model.SearchItem

interface SearchRepository {
    suspend fun searchGames(query: String): List<SearchItem>
    suspend fun searchHistory(): List<String>
}