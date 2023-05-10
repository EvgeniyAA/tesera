package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.core.utils.toDate
import com.tesera.domain.model.CommentModel
import java.text.SimpleDateFormat
import java.util.*

data class CommentResponse(
    val teseraId: Int? = null,
    val id: Int? = null,
    val parentId: Int? = null,
    val title: String? = null,
    val content: String? = null,
    val rating: Int? = null,
    val creationDateUtc: String? = null,
    val author: AuthorResponse? = null,
    @SerializedName("object") val teseraObject: TeseraObjectResponse? = null,
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
    teseraObject = this?.teseraObject.toModel()
)