package com.tesera.domain.model

import androidx.compose.runtime.Stable

@Stable
data class GamePreview(
    val id: Int,
    val bggId: Int,
    val title: String,
    val title2: String,
    val year: Int,
    val photoUrl: String,
    val commentsTotal: Int,
    val commentsTotalNew: Int,
    val n10Rating: Double,
    val alias: String,
    val isAddition: Boolean = false,
)