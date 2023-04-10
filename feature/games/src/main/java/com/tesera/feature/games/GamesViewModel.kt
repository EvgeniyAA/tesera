package com.tesera.feature.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tesera.domain.games.GamePreviewModel
import com.tesera.domain.games.GamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val gamesUseCase: GamesUseCase
) : ViewModel() {

    fun getGames(): Flow<PagingData<GamePreviewModel>> =
        gamesUseCase.getHotnessGames().cachedIn(viewModelScope)
}