package com.tesera.data.network.model.response

import com.tesera.domain.model.PhotoModel

data class PhotoResponse(
    val teseraId: Int? = null,
    val title: String? = null,
    val photoUrl: String? = null,
    val creationDateUtc: String? = null,
    val commentsTotal: Int? = null,
    val author: AuthorResponse? = AuthorResponse(),
)

fun PhotoResponse?.toPhotoModel() = PhotoModel(
    teseraId = this?.teseraId ?: 0,
    title = this?.title.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty(),
    commentsTotal = this?.commentsTotal ?: 0,
    author = this?.author.toAuthorModel()
)