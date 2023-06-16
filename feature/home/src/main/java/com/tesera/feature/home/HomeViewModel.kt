package com.tesera.feature.home

import androidx.lifecycle.viewModelScope
import com.tesera.core.mvi.MviViewModel
import com.tesera.core.mvi.Reducer
import com.tesera.domain.home.HomeUseCase
import com.tesera.feature.home.models.HomeIntent
import com.tesera.feature.home.models.HomeIntent.PartialState
import com.tesera.feature.home.models.HomeViewState
import com.tesera.feature.home.models.mapToIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase,
) : MviViewModel<HomeViewState, HomeIntent>() {

    private val reducer = HomeReducer(HomeViewState())

    override val state: StateFlow<HomeViewState>
        get() = reducer.state

    init {
        getContent()
    }

    fun getContent() {
        viewModelScope.launch {
            useCase.getContent().collect {
                obtainIntent(it.mapToIntent())
            }
        }
    }

    fun getGames() {
        viewModelScope.launch {
            useCase.latestHotnessGames().collect {
                obtainIntent(it.mapToIntent())
            }
        }
    }

    fun getNews() {
        viewModelScope.launch {
            useCase.latestNews().collect {
                obtainIntent(it.mapToIntent())
            }
        }
    }

    private inner class HomeReducer(initial: HomeViewState) :
        Reducer<HomeViewState, HomeIntent>(initial) {

        override fun reduce(
            oldState: HomeViewState,
            intent: HomeIntent,
        ) {
            when (intent) {
                is PartialState.Games -> setState(
                    oldState.copy(
                        hotnessGames = intent.games,
                        isGamesLoading = false,
                        gamesLoadingError = null
                    )
                )

                PartialState.GamesLoading -> setState(
                    oldState.copy(
                        isGamesLoading = true,
                        gamesLoadingError = null,
                        hotnessGames = emptyList()
                    )
                )

                is PartialState.GamesLoadingError -> setState(
                    oldState.copy(
                        gamesLoadingError = intent.error,
                        isGamesLoading = false
                    )
                )

                is PartialState.News -> setState(
                    oldState.copy(
                        news = intent.news,
                        isNewsLoading = false,
                        newsLoadingError = null
                    )
                )

                PartialState.NewsLoading -> setState(
                    oldState.copy(
                        isNewsLoading = true,
                        newsLoadingError = null,
                        news = emptyList()
                    )
                )

                is PartialState.NewsLoadingError -> setState(
                    oldState.copy(
                        newsLoadingError = intent.error,
                        isNewsLoading = false
                    )
                )
            }
        }
    }

    override fun obtainIntent(intent: HomeIntent) {
        reducer.sendIntent(intent)
    }
}