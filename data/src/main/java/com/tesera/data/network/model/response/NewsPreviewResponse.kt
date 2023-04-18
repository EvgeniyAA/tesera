package com.tesera.data.network.model.response

import com.tesera.domain.model.NewsPreviewModel

data class NewsPreviewResponse(
    val teseraId: Int? = null,
    val title: String? = null,
    val title2: String? = null,
    val alias: String? = null,
    val contentShort: String? = null,
    val creationDateUtc: String? = null,
    val modificationDateUtc: String? = null,
    val publicationDateUtc: String? = null,
    val photoUrl: String? = null,
    val rating: Double? = null,
    val commentsTotal: Int? = null,
    val numVotes: Int? = null
)

fun NewsPreviewResponse?.toModel() = NewsPreviewModel(
    teseraId = this?.teseraId ?: 0,
    title = this?.title ?: this?.title2.orEmpty(),
    title2 = this?.title2.orEmpty(),
    alias = this?.alias.orEmpty(),
    contentShort = this?.contentShort.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty(),
    modificationDateUtc = this?.modificationDateUtc.orEmpty(),
    publicationDateUtc = this?.publicationDateUtc.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    rating = this?.rating ?: 0.0,
    commentsTotal = this?.commentsTotal ?: 0,
    numVotes = this?.numVotes ?: 0
)