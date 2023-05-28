package com.tesera.feature.search

import androidx.lifecycle.viewModelScope
import com.tesera.core.mvi.MviViewModel
import com.tesera.core.mvi.Reducer
import com.tesera.domain.search.SearchPartialState
import com.tesera.domain.search.SearchUseCase
import com.tesera.feature.search.models.SearchIntent
import com.tesera.feature.search.models.SearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchUseCase,
) : MviViewModel<SearchViewState, SearchIntent>() {
    private val reducer = SearchReducer(SearchViewState())

    override val state: StateFlow<SearchViewState>
        get() = reducer.state

    init {
        viewModelScope.launch {
            useCase.observeSearchResults().collect {
                when (it) {
                    is SearchPartialState.SearchHistory -> obtainIntent(
                        SearchIntent.PartialState.History(it.history)
                    )

                    is SearchPartialState.SearchResult -> obtainIntent(
                        SearchIntent.PartialState.Result(it.result)
                    )
                }
            }
        }
    }

    override fun obtainIntent(intent: SearchIntent) {
        reducer.sendIntent(intent)
    }

    fun onSearchTextChanged(query: String) {
        viewModelScope.launch {
            useCase.search(query)
        }
    }


    private inner class SearchReducer(initial: SearchViewState) :
        Reducer<SearchViewState, SearchIntent>(initial) {
        override fun reduce(oldState: SearchViewState, intent: SearchIntent) {
            Timber.d(intent.toString())
            when (intent) {
                is SearchIntent.UiActions.SearchTextChanged -> {
                    val state = oldState.copy(
                        isLoading = intent.query.isValidQuery(),
                        searchResult = emptyList(),
                        query = intent.query,
                        isEmpty = if (intent.query.isValidQuery()) oldState.isEmpty else false,
                        history = if (intent.query.isValidQuery()) emptyList() else oldState.history
                    )
                    setState(state)
                    if (intent.query.isValidQuery()) {
                        onSearchTextChanged(intent.query)
                    }
                }

                is SearchIntent.PartialState.Result -> setState(
                    oldState.copy(
                        searchResult = if(oldState.query.isValidQuery()) intent.list else emptyList(),
                        isLoading = false,
                        isEmpty = intent.list.isEmpty(),
                        history = emptyList()
                    )
                )

                is SearchIntent.PartialState.History -> setState(oldState.copy(history = intent.list))
            }
        }

        private fun String.isValidQuery() = this.isNotEmpty() && this.trim().length > 2
    }
}