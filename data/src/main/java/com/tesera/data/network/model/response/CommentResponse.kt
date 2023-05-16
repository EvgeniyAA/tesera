package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.core.utils.toDate
import com.tesera.domain.model.CommentModel
import java.text.SimpleDateFormat
import java.util.*

data class CommentResponse(
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("parentId") val parentId: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("rating") val rating: Int? = null,
    @SerializedName("creationDateUtc") val creationDateUtc: String? = null,
    @SerializedName("author") val author: AuthorResponse? = null,
//    @SerializedName("object") val teseraObject: TeseraObjectResponse? = null,
)

fun CommentResponse?.toModel() = CommentModel(
    teseraId = this?.teseraId ?: 0,
    id = this?.id ?: 0,
    parentId = this?.parentId,
    title = this?.title,
    content = this?.content.orEmpty(),
    rating = this?.rating ?: 0,
    creationDateUtc = this?.creationDateUtc.orEmpty().toDate(),
    author = this?.author.toAuthorModel(),
//    teseraObject = this?.teseraObject.toModel()
)