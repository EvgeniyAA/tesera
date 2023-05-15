package com.tesera.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesera.core.mvi.IntentHandler
import com.tesera.domain.home.HomePartialState
import com.tesera.domain.home.HomeUseCase
import com.tesera.feature.home.models.HomeIntent
import com.tesera.feature.home.models.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase,
) : ViewModel(), IntentHandler<HomeIntent> {

    private val _state: MutableStateFlow<HomeViewState> =
        MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState> = _state

    init {
        getContent()
    }

    fun getContent() {
        viewModelScope.launch {
            var oldState = _state.value
            useCase.getContent().collect {
                val newState = reduce(oldState, it)
                oldState = newState
                sendViewState(newState)
            }
        }
    }

    fun getGames() {
        viewModelScope.launch {
            var oldState = _state.value
            useCase.latestHotnessGames().collect {
                val newState = reduce(oldState, it)
                oldState = newState
                sendViewState(newState)
            }
        }
    }

    fun getNews() {
        viewModelScope.launch {
            var oldState = _state.value
            useCase.latestNews().collect {
                val newState = reduce(oldState, it)
                oldState = newState
                sendViewState(newState)
            }
        }
    }

    private fun reduce(oldState: HomeViewState, partialState: HomePartialState): HomeViewState {
        println(partialState.toString())
        return when (partialState) {
            is HomePartialState.Games -> oldState.copy(
                hotnessGames = partialState.games,
                isGamesLoading = false,
                gamesLoadingError = null
            )

            HomePartialState.GamesLoading -> oldState.copy(
                isGamesLoading = true,
                gamesLoadingError = null,
                hotnessGames = emptyList()
            )

            is HomePartialState.GamesLoadingError -> oldState.copy(
                gamesLoadingError = partialState.error,
                isGamesLoading = false
            )

            is HomePartialState.News -> oldState.copy(
                news = partialState.news,
                isNewsLoading = false,
                newsLoadingError = null
            )

            HomePartialState.NewsLoading -> oldState.copy(
                isNewsLoading = true,
                newsLoadingError = null,
                news = emptyList()
            )

            is HomePartialState.NewsLoadingError -> oldState.copy(
                newsLoadingError = partialState.error,
                isNewsLoading = false
            )
        }
    }

    override fun obtainIntent(intent: HomeIntent) {}//=
//        when (intent) {
//            HomeIntent.GameListClicked -> gameList()
//            HomeIntent.NewsListClicked -> newsList()
//            HomeIntent.ActionInvoked -> sendViewState(_homeViewState.value.copy(action = HomeAction.None))
//            is HomeIntent.GameDetailsClicked -> sendViewState(
//                _homeViewState.value.copy(action = HomeAction.ToGameDetails(intent.game))
//            )
//            is HomeIntent.NewsDetailsClicked -> sendViewState(
//                _homeViewState.value.copy(action = HomeAction.ToNewsDetails(intent.news))
//            )
//        }

//    private fun gameList() {
//        gamesFilterUseCase.setFilter(
//            GamesFilter(
//                type = GamesType.HOTNESSBGG,
//                sort = GamesType.HOTNESS
//            )
//        )
//        sendViewState(_homeViewState.value.copy(action = HomeAction.ToGamesList))
//    }
//
//    private fun newsList() {
//        sendViewState(_homeViewState.value.copy(action = HomeAction.ToNewsList))
//    }

    private fun sendViewState(viewState: HomeViewState) {
        viewModelScope.launch {
            _state.value = viewState
        }
    }
}