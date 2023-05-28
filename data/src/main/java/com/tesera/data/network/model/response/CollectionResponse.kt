package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.CollectionItem
import com.tesera.domain.model.CollectionType

data class CollectionResponse(
    @SerializedName("collectionType") val collectionType: CollectionType? = null,
    @SerializedName("gamesTotal") val gamesTotal: Int? = null,
    @SerializedName("selfGamesCount") val selfGamesCount: Int? = null,
    @SerializedName("additionsCount") val additionsCount: Int? = null,
)

fun CollectionResponse?.toModel() = CollectionItem(
    collectionType = this?.collectionType ?: CollectionType.Own,
    gamesTotal = this?.gamesTotal ?: 0,
    selfGamesCount = this?.selfGamesCount ?: 0,
    additionsCount = this?.additionsCount ?: 0
)