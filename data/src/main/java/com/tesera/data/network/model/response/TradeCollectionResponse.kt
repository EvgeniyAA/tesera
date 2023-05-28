package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.MarketItemType
import com.tesera.domain.model.TradeCollectionItem

data class TradeCollectionResponse(
    @SerializedName("collectionType") val collectionType: MarketItemType? = null,
    @SerializedName("gamesTotal") val gamesTotal: Int? = null,
    @SerializedName("gamesTotalAll") val gamesTotalAll: Int? = null,
    @SerializedName("selfGamesCount") val selfGamesCount: Int? = null,
    @SerializedName("additionsCount") val additionsCount: Int? = null,
    @SerializedName("items") val items: List<MarketItemResponse> = emptyList(),
)

fun TradeCollectionResponse?.toModel() = TradeCollectionItem(
    collectionType = this?.collectionType ?: MarketItemType.Sales,
    gamesTotal = this?.gamesTotal ?: 0,
    gamesTotalAll = this?.gamesTotalAll ?: 0,
    selfGamesCount = this?.selfGamesCount ?: 0,
    additionsCount = this?.additionsCount ?: 0,
    items = this?.items?.map { it.toModel() } ?: emptyList()
)