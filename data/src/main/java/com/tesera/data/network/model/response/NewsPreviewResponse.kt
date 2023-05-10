package com.tesera.data.network.model.response

import com.tesera.core.utils.toDate
import com.tesera.domain.model.NewsPreviewModel
import com.tesera.domain.model.NewsType

data class NewsPreviewResponse(
    val objectId: Int? = null,
    val teseraId: Int? = null,
    val objectType: NewsType? = null,
    val title: String? = null,
    val title2: String? = null,
    val alias: String? = null,
    val contentShort: String? = null,
    val creationDateUtc: String? = null,
    val photoUrl: String? = null,
    val rating: Double? = null,
    val commentsTotal: Int? = null,
    val numVotes: Int? = null,
    val author: AuthorResponse? = null,
)

fun NewsPreviewResponse?.toModel() = NewsPreviewModel(
    objectId = this?.objectId ?: this?.teseraId ?: 0,
    objectType = this?.objectType ?: NewsType.None,
    title = this?.title ?: this?.title2.orEmpty(),
    title2 = this?.title2.orEmpty(),
    alias = this?.alias.orEmpty(),
    contentShort = this?.contentShort.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty().toDate(),
    photoUrl = this?.photoUrl.orEmpty(),
    rating = this?.rating ?: 0.0,
    commentsTotal = this?.commentsTotal ?: 0,
    numVotes = this?.numVotes ?: 0,
    authorModel = this?.author.toAuthorModel()
)