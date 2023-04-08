package com.tesera.domain.games

data class GamePreviewModel(
    val id: Int,
    val title: String,
    val title2: String,
    val year: Int,
    val photoUrl: String,
    val commentsTotal: Int,
    val commentsTotalNew: Int,
    val n10Rating: Double
)