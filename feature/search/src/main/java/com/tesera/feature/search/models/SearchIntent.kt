package com.tesera.feature.search.models

import com.tesera.core.mvi.UiIntent
import com.tesera.domain.model.SearchItem

sealed class SearchIntent : UiIntent {
    sealed class UiActions : SearchIntent() {
        data class SearchTextChanged(val query: String) : UiActions()
    }

    sealed class PartialState : SearchIntent() {
        data class Result(val list: List<SearchItem>) : PartialState()
        data class History(val list: List<String>) : PartialState()
    }
}