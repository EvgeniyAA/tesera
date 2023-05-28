package com.tesera.feature.search.models

import androidx.compose.runtime.Stable
import com.tesera.core.mvi.UiState
import com.tesera.domain.model.SearchItem

@Stable
data class SearchViewState(
    val query: String = "",
    val searchResult: List<SearchItem> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val history: List<String> = emptyList()
) : UiState