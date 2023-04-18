package com.tesera.data.network.model.response

import com.tesera.domain.model.LinkModel

data class LinkResponse(
    val teseraId: Int? = null,
    val objectType: String? = null,
    val title: String? = null,
    val photoUrl: String? = null,
    val modificationDateUtc: String? = null,
    val creationDateUtc: String? = null,
    val author: AuthorResponse? = AuthorResponse(),
)

fun LinkResponse?.toLinkModel() = LinkModel(
    teseraId = this?.teseraId ?: 0,
    objectType = this?.objectType.orEmpty(),
    title = this?.title.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    modificationDateUtc = this?.modificationDateUtc.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty(),
    author = this?.author.toAuthorModel()
)