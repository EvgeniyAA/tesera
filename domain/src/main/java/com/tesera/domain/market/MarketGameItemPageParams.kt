package com.tesera.domain.market

data class MarketGameItemPageParams(
    val type: MarketType,
    val limit: Int = 30,
    val offset: Int = 0,
    val alias: String? = null,
    val user: String? = null,
)