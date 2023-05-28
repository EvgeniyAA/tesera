package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.UserReportsInfo

data class UserReportsInfoResponse(
    @SerializedName("readyCount") val readyCount: Int? = null,
    @SerializedName("draftCount") val draftCount: Int? = null,
    @SerializedName("total") val total: Int? = null,
)

fun UserReportsInfoResponse?.toModel() = UserReportsInfo(
    readyCount = this?.readyCount ?: 0,
    draftCount = this?.draftCount ?: 0,
    total = this?.total ?: 0
)