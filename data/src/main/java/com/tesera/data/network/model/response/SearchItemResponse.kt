package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.SearchItem

data class SearchItemResponse(
    @SerializedName("type") val type: String? = null,
    @SerializedName("value") val value: String? = null,
    @SerializedName("alias") val alias: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("title2") val title2: String? = null,
    @SerializedName("photoUrl") val photoUrl: String? = null
)

fun SearchItemResponse?.toModel() = SearchItem(
    type = this?.type.orEmpty(),
    value = this?.value.orEmpty(),
    alias = this?.alias.orEmpty(),
    id = this?.id ?: 0,
    teseraId = this?.teseraId ?: 0,
    title = this?.title.orEmpty(),
    title2 = this?.title2.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty()
)