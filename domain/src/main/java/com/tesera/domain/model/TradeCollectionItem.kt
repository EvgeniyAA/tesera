package com.tesera.domain.model

data class TradeCollectionItem(
    val collectionType: MarketItemType,
    val gamesTotal: Int,
    val gamesTotalAll: Int,
    val selfGamesCount: Int,
    val additionsCount: Int,
    val items: List<MarketGameItem>,
)