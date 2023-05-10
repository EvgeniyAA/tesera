package com.tesera.data.network.model.response

import com.tesera.domain.model.NewsModel

data class NewsResponse(
    val teseraId: Int? = null,
    val title: String? = null,
    val alias: String? = null,
    val content: String? = null,
    val creationDateUtc: String? = null,
    val modificationDateUtc: String? = null,
    val publicationDateUtc: String? = null,
    val photoUrl: String? = null,
    val rating: Double? = null,
    val commentsTotal: Int? = null,
    val numVotes: Int? = null,
)

fun NewsResponse?.toNewsModel() = NewsModel(
    teseraId = this?.teseraId ?: 0,
    title = this?.title.orEmpty(),
    alias = this?.alias.orEmpty(),
    content = this?.content.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty(),
    modificationDateUtc = this?.modificationDateUtc.orEmpty(),
    publicationDateUtc = this?.publicationDateUtc.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    rating = this?.rating ?: 0.0,
    commentsTotal = this?.commentsTotal ?: 0,
    numVotes = this?.numVotes ?: 0
)