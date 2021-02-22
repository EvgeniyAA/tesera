package com.boardgames.tesera.data.model

import com.squareup.moshi.Json

enum class SortType {
    @Json(name = "hotness")
    HOTNESS,
    @Json(name = "-creationdateutc")
    NEW

}