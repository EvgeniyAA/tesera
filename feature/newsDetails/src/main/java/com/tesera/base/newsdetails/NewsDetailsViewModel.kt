package com.tesera.base.newsdetails

import androidx.lifecycle.viewModelScope
import com.tesera.base.newsdetails.models.NewsDetailsIntent
import com.tesera.base.newsdetails.models.NewsDetailsViewState
import com.tesera.core.mvi.MviViewModel
import com.tesera.core.mvi.Reducer
import com.tesera.core.mvi.TimeCapsule
import com.tesera.domain.news.NewsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val newsUseCase: NewsDetailsUseCase,
) : MviViewModel<NewsDetailsViewState, NewsDetailsIntent>() {
    private val reducer = NewsDetailsReducer(NewsDetailsViewState())

    override val state: StateFlow<NewsDetailsViewState>
        get() = reducer.state

    val timeMachine: TimeCapsule<NewsDetailsViewState>
        get() = reducer.timeCapsule

    override fun obtainIntent(intent: NewsDetailsIntent) {
        reducer.sendIntent(intent)
    }

    private inner class NewsDetailsReducer(initial: NewsDetailsViewState) :
        Reducer<NewsDetailsViewState, NewsDetailsIntent>(initial) {
        override fun reduce(oldState: NewsDetailsViewState, intent: NewsDetailsIntent) =
            when (intent) {
                is NewsDetailsIntent.GetNewsDetails -> getNewsDetails(oldState, intent)
            }

        private fun getNewsDetails(
            oldState: NewsDetailsViewState,
            intent: NewsDetailsIntent.GetNewsDetails,
        ) {
            viewModelScope.launch {
                val result = newsUseCase.getNewsDetails(intent.type, intent.alias)
                setState(oldState.copy(isLoading = false, newsDetails = result))
            }
        }
    }
}