package com.tesera.data.network.model.response

import com.tesera.domain.model.TeseraObjectModel

data class TeseraObjectResponse(
    val id: Int? = null,
    val teseraId: Int? = null,
    val commentsTotal: Int? = null,
    val pictureUrl: String? = null,
    val alias: String? = null,
    val title: String? = null,
    val objectType: String? = null
)

fun TeseraObjectResponse?.toModel() = TeseraObjectModel(
    id = this?.id ?: 0,
    teseraId = this?.teseraId ?: 0,
    commentsTotal = this?.commentsTotal ?: 0,
    pictureUrl = this?.pictureUrl.orEmpty(),
    alias = this?.alias.orEmpty(),
    title = this?.title.orEmpty(),
    objectType = this?.objectType.orEmpty()
)