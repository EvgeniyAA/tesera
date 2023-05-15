package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.core.utils.toDate
import com.tesera.domain.model.NewsPreview
import com.tesera.domain.model.NewsType

data class NewsPreviewResponse(
    @SerializedName("objectId") val objectId: Int? = null,
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("objectType") val objectType: NewsType? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("title2") val title2: String? = null,
    @SerializedName("alias") val alias: String? = null,
    @SerializedName("contentShort") val contentShort: String? = null,
    @SerializedName("creationDateUtc") val creationDateUtc: String? = null,
    @SerializedName("photoUrl") val photoUrl: String? = null,
    @SerializedName("rating") val rating: Double? = null,
    @SerializedName("commentsTotal") val commentsTotal: Int? = null,
    @SerializedName("numVotes") val numVotes: Int? = null,
    @SerializedName("author") val author: AuthorResponse? = null,
)

fun NewsPreviewResponse?.toModel() = NewsPreview(
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
    author = this?.author.toAuthorModel()
)