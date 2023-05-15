package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.GameFile

data class FileResponse(
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("objectType") val objectType: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("photoUrl") val photoUrl: String? = null,
    @SerializedName("modificationDateUtc") val modificationDateUtc: String? = null,
    @SerializedName("creationDateUtc") val creationDateUtc: String? = null,
    @SerializedName("author") val author: AuthorResponse? = AuthorResponse(),
)

fun FileResponse?.toFileModel(alias: String?) = GameFile(
    teseraId = this?.teseraId ?: 0,
    objectType = this?.objectType.orEmpty(),
    title = this?.title.orEmpty(),
    content = this?.content.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    modificationDateUtc = this?.modificationDateUtc.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty(),
    author = this?.author.toAuthorModel(),
    alias = alias.orEmpty()
)