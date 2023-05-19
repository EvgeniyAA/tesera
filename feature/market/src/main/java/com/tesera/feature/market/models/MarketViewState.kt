package com.tesera.feature.market.models

import com.tesera.domain.market.MarketType

data class MarketViewState(
    val type: MarketType? = null,
    val user: String? = null
)