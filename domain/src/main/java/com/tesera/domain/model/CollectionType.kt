package com.tesera.domain.model

import com.google.gson.annotations.SerializedName

enum class CollectionType {
    @SerializedName("Own")
    Own,

    @SerializedName("Played")
    Played,

    @SerializedName("ToPlay")
    ToPlay,

    @SerializedName("Top")
    Top
}