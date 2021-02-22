package com.boardgames.tesera.data.model

import com.squareup.moshi.Json

enum class GameType {
    @Json(name = "hotness")
    HOTNESS,
    @Json(name = "hotnessbgg")
    HOTNESSBGG
}