package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.TeseraObjectModel

data class TeseraObjectResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("commentsTotal") val commentsTotal: Int? = null,
    @SerializedName("pictureUrl") val pictureUrl: String? = null,
    @SerializedName("alias") val alias: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("objectType") val objectType: String? = null
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