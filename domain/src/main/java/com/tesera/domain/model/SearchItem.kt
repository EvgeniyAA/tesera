package com.tesera.domain.model

import androidx.compose.runtime.Stable

@Stable
data class SearchItem(
    val type: String,
    val value: String,
    val alias: String,
    val id: Int,
    val teseraId: Int,
    val title: String,
    val title2: String,
    val photoUrl: String
)