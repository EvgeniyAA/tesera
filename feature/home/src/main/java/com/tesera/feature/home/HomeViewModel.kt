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
import com.tesera.feature.home.models.HomeIntent
import com.tesera.feature.home.models.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gamesUseCase: GamesUseCase
) : ViewModel(), IntentHandler<HomeIntent> {

    private val _homeViewState: MutableStateFlow<HomeViewState> =
        MutableStateFlow(HomeViewState.Loading)
    val homeViewState: StateFlow<HomeViewState> = _homeViewState

    fun getHotnessGames(): Flow<PagingData<GamePreviewModel>> =
        gamesUseCase.getLastHotnessGames1().cachedIn(viewModelScope)

    override fun obtainIntent(intent: HomeIntent) =
        when (val currentState = _homeViewState.value) {
            is HomeViewState.Loading -> reduce(intent, currentState)
            else -> TAG.logInvalidIntent(intent, currentState)
        }

    private fun reduce(intent: HomeIntent, currentState: HomeViewState.Loading) {
        when (intent) {
            is HomeIntent.GetContent -> {
//                viewModelScope.launch {
//                    gamesUseCase.getLastHotnessGames1().collect {
//                        sendViewState(HomeViewState.Content(it))
//                    }
//                }
            }
            else -> TAG.logInvalidIntent(intent, currentState)
        }
    }

    private fun sendViewState(viewState: HomeViewState) {
        viewModelScope.launch {
            _homeViewState.emit(viewState)
        }
    }
}