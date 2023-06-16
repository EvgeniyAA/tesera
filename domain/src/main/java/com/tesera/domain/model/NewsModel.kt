package com.tesera.domain.model

import androidx.compose.runtime.Stable

@Stable
data class NewsModel(
    val teseraId: Int,
    val title: String,
    val alias: String,
    val content: String,
    val creationDateUtc: String,
    val modificationDateUtc: String,
    val publicationDateUtc: String,
    val photoUrl: String,
    val rating: Double,
    val commentsTotal: Int,
    val numVotes: Int
)