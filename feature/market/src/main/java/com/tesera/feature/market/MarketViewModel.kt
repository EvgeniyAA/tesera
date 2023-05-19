package com.tesera.feature.market

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tesera.core.constants.KEY_ALIAS
import com.tesera.core.constants.KEY_MARKET_TYPE
import com.tesera.core.constants.KEY_USER
import com.tesera.domain.market.MarketType
import com.tesera.domain.market.MarketUseCase
import com.tesera.domain.model.MarketGameItem
import com.tesera.feature.market.models.MarketViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: MarketUseCase,
) : ViewModel() {
    private val type = savedStateHandle.get<MarketType>(KEY_MARKET_TYPE) ?: MarketType.Buy
    private val alias = savedStateHandle.get<String>(KEY_ALIAS)
    private val user = savedStateHandle.get<String>(KEY_USER)

    val marketItems: Flow<PagingData<MarketGameItem>> =
        useCase.getMarketItems(type, alias, user).cachedIn(viewModelScope)

    private val _state: MutableStateFlow<MarketViewState> = MutableStateFlow(MarketViewState())
    val state: StateFlow<MarketViewState> = _state

    init {
        _state.value = MarketViewState(type = type, user = user)
    }
}