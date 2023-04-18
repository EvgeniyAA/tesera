package com.tesera.domain.model

data class GamePreviewModel(
    val id: Int,
    val bggId: Int,
    val title: String,
    val title2: String,
    val year: Int,
    val photoUrl: String,
    val commentsTotal: Int,
    val commentsTotalNew: Int,
    val n10Rating: Double,
    val alias: String
)