package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.core.utils.toDate
import com.tesera.domain.model.GameOwner

data class GameOwnerResponse(
    @SerializedName("teseraId") val teseraId: Int?,
    @SerializedName("login") val login: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("rating") val rating: Double?,
    @SerializedName("comment") val comment: String?,
    @SerializedName("eventDate") val eventDate: String?,
    @SerializedName("avatarUrl") val avatarUrl: String?,
)

fun GameOwnerResponse?.toModel() = GameOwner(
    teseraId = this?.teseraId ?: 0,
    login = this?.login.orEmpty(),
    name = this?.name.orEmpty(),
    rating = this?.rating ?: 0.0,
    comment = this?.comment.orEmpty(),
    eventDate = this?.eventDate?.toDate(),
    avatarUrl = this?.avatarUrl
)