package com.tesera.domain.market

import javax.inject.Inject

class MarketUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
) {
    fun getMarketItems(type: MarketType, alias: String? = null, user: String? = null) =
        marketRepository.getMarketItems(
            MarketGameItemPageParams(type = type, alias = alias, user = user)
        )
}