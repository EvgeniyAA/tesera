package com.tesera.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tesera.core.extensions.TAG
import com.tesera.core.mvi.IntentHandler
import com.tesera.core.mvi.logInvalidIntent
import com.tesera.domain.games.GamePreviewModel
import com.tesera.domain.games.GamesUseCase
import com.tesera.domain.news.NewsPreviewModel
import com.tesera.domain.news.NewsUseCase
import com.tesera.feature.home.models.HomeAction
import com.tesera.feature.home.models.HomeIntent
import com.tesera.feature.home.models.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gamesUseCase: GamesUseCase,
    private val newsUseCase: NewsUseCase
) : ViewModel(), IntentHandler<HomeIntent> {

    private val _homeViewState: MutableStateFlow<HomeViewState> =
        MutableStateFlow(HomeViewState())
    val homeViewState: StateFlow<HomeViewState> = _homeViewState

    private suspend fun getHotnessGames() = gamesUseCase.getLatestHotnessGames().collect {
        sendViewState(_homeViewState.value.copy(hotnessGames = it))
    }

    private suspend fun getNews() = newsUseCase.getLatestNews().collect {
        sendViewState(_homeViewState.value.copy(news = it))

    }

    init {
        viewModelScope.launch {
            getHotnessGames()
            getNews()
        }
    }

    override fun obtainIntent(intent: HomeIntent) =
        when (intent) {
            HomeIntent.GameListClicked -> gameList()
            HomeIntent.NewsListClicked -> newsList()
            HomeIntent.ActionInvoked -> sendViewState(_homeViewState.value.copy(action = HomeAction.None))
        }

    private fun gameList() {
        sendViewState(_homeViewState.value.copy(action = HomeAction.ToGamesList))
    }

    private fun newsList() {
        sendViewState(_homeViewState.value.copy(action = HomeAction.ToNewsList))
    }

    private fun sendViewState(viewState: HomeViewState) {
        viewModelScope.launch {
            _homeViewState.emit(viewState)
        }
    }
}