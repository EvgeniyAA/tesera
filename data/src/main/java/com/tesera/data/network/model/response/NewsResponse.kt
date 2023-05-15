package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.NewsModel

data class NewsResponse(
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("alias") val alias: String? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("creationDateUtc") val creationDateUtc: String? = null,
    @SerializedName("modificationDateUtc") val modificationDateUtc: String? = null,
    @SerializedName("publicationDateUtc") val publicationDateUtc: String? = null,
    @SerializedName("photoUrl") val photoUrl: String? = null,
    @SerializedName("rating") val rating: Double? = null,
    @SerializedName("commentsTotal") val commentsTotal: Int? = null,
    @SerializedName("numVotes") val numVotes: Int? = null,
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