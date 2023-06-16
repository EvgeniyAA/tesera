package com.tesera.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Photo(
    val teseraId: Int,
    val title: String,
    val photoUrl: String,
    val creationDateUtc: String,
    val commentsTotal: Int,
    val author: Author
)