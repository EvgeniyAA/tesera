package com.tesera.feature.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tesera.core.mvi.IntentHandler
import com.tesera.domain.model.NewsPreviewModel
import com.tesera.domain.news.NewsUseCase
import com.tesera.feature.news.models.NewsAction
import com.tesera.feature.news.models.NewsIntent
import com.tesera.feature.news.models.NewsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
) : ViewModel(), IntentHandler<NewsIntent> {

    fun getNews(): Flow<PagingData<NewsPreviewModel>> =
        newsUseCase.getNews().cachedIn(viewModelScope)

    private val _viewState: MutableStateFlow<NewsViewState> =
        MutableStateFlow(NewsViewState())
    val viewState: StateFlow<NewsViewState> = _viewState

    override fun obtainIntent(intent: NewsIntent) = when (intent) {
        NewsIntent.ActionInvoked ->
            sendViewState(_viewState.value.copy(action = NewsAction.None))
        is NewsIntent.NewsDetailsClicked -> sendViewState(
            _viewState.value.copy(action = NewsAction.ToNewsDetails(intent.news))
        )
    }

    private fun sendViewState(viewState: NewsViewState) {
        viewModelScope.launch {
            _viewState.emit(viewState)
        }
    }
}