package com.tesera.domain.model

import androidx.compose.runtime.Stable
import java.util.Date

@Stable
data class MarketGameItem(
    val type: MarketItemType,
    val value: String,
    val alias: String,
    val id: Int,
    val relationId: Int,
    val content: String?,
    val publicationDateUtc: Date,
    val price: Double?,
    val country: Location,
    val city: Location,
    val currency: String?,
    val status: String,
    val condition: ConditionType = ConditionType.Unknown,
    val game: Game,
    val author: Author
)