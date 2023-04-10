package com.tesera.domain.news

data class NewsPreviewModel(
    val teseraId: Int,
    val title: String,
    val title2: String,
    val alias: String,
    val contentShort: String,
    val creationDateUtc: String,
    val modificationDateUtc: String,
    val publicationDateUtc: String,
    val photoUrl: String,
    val rating: Double,
    val commentsTotal: Int,
    val numVotes: Int
)