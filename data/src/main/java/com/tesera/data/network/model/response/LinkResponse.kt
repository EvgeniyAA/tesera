package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.Link

data class LinkResponse(
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("objectType") val objectType: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("photoUrl") val photoUrl: String? = null,
    @SerializedName("modificationDateUtc") val modificationDateUtc: String? = null,
    @SerializedName("creationDateUtc") val creationDateUtc: String? = null,
    @SerializedName("author") val author: AuthorResponse? = AuthorResponse(),
)

fun LinkResponse?.toLinkModel() = Link(
    teseraId = this?.teseraId ?: 0,
    objectType = this?.objectType.orEmpty(),
    title = this?.title.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    modificationDateUtc = this?.modificationDateUtc.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty(),
    author = this?.author.toAuthorModel()
)