package com.tesera.domain.games.filters

data class GamesFilter(
    val type: GamesType,
    val sort: GamesType = type,
    val limit: Int = 15,
    val offset: Int = 0,
    val limited: Boolean = false,
)


enum class GamesType(val value: String) {
    HOTNESS("hotness"), HOTNESSBGG("hotnessbgg"), NEW("-creationdateutc")
}