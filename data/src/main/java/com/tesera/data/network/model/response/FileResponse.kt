package com.tesera.data.network.model.response

import com.tesera.domain.model.FileModel

data class FileResponse(
    val teseraId: Int? = null,
    val objectType: String? = null,
    val title: String? = null,
    val content: String? = null,
    val photoUrl: String? = null,
    val modificationDateUtc: String? = null,
    val creationDateUtc: String? = null,
    val author: AuthorResponse? = AuthorResponse(),
)

fun FileResponse?.toFileModel(alias: String?) = FileModel(
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