package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.Location

data class LocationResponse(
    @SerializedName("type") val type: String? = null,
    @SerializedName("value") val value: String? = null,
    @SerializedName("alias") val alias: String? = null,
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("name2") val name2: String? = null,
)

fun LocationResponse?.toModel() = Location(
    type = this?.type.orEmpty(),
    value = this?.value.orEmpty(),
    alias = this?.alias.orEmpty(),
    teseraId = this?.teseraId ?: 0,
    name = this?.name.orEmpty(),
    name2 = this?.name2.orEmpty()
)