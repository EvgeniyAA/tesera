package com.tesera.domain.games

data class GamePageParams(
    val limit: Int = 60,
    val offset: Int = 0,
    val type: String,
    val sort: String
)