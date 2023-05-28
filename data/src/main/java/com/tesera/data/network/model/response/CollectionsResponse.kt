package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.Collections

data class CollectionsResponse(
    @SerializedName("collections") val collections: List<CollectionResponse> = emptyList(),
    @SerializedName("tradeCollections") val tradeCollections: List<TradeCollectionResponse> = emptyList(),
)

fun CollectionsResponse?.toModel() = Collections(
    collections = this?.collections?.map { it.toModel() } ?: emptyList(),
    tradeCollections = this?.tradeCollections?.map { it.toModel() } ?: emptyList()
)