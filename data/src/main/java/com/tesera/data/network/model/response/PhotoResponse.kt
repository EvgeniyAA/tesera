package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.Photo

data class PhotoResponse(
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("photoUrl") val photoUrl: String? = null,
    @SerializedName("creationDateUtc") val creationDateUtc: String? = null,
    @SerializedName("commentsTotal") val commentsTotal: Int? = null,
    @SerializedName("author") val author: AuthorResponse? = AuthorResponse(),
)

fun PhotoResponse?.toPhotoModel() = Photo(
    teseraId = this?.teseraId ?: 0,
    title = this?.title.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty(),
    commentsTotal = this?.commentsTotal ?: 0,
    author = this?.author.toAuthorModel()
)