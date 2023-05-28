package com.tesera.domain.model

data class Profile(
    val user: User,
    val gamesInCollection: Int,
    val sales: List<MarketGameItem>,
    val purchases: List<MarketGameItem>
)
