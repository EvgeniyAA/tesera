package com.tesera.domain.model

import com.google.gson.annotations.SerializedName

enum class MarketItemType {
    @SerializedName("Sales")
    Sales,

    @SerializedName("Purchases")
    Purchases,
}