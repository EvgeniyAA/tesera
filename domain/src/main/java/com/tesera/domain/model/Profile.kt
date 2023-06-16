package com.tesera.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Profile(
    val user: User,
    val photos: List<Photo>,
    val gamesInCollection: Int,
    val sales: List<MarketGameItem>,
    val purchases: List<MarketGameItem>
)
