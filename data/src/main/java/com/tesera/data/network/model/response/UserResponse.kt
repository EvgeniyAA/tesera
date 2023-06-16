package com.tesera.data.network.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tesera.core.utils.toDate
import com.tesera.domain.model.User

@Keep
data class UserResponse(
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("login") val login: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("comment") val comment: String? = null,
    @SerializedName("avatarUrl") val avatarUrl: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("rating") val rating: Int? = null,
    @SerializedName("experience") val experience: Int? = null,
    @SerializedName("creationDateUtc") val creationDateUtc: String? = null,
    @SerializedName("modificationDateUtc") val modificationDateUtc: String? = null,
    @SerializedName("collectionCount") val collectionCount: Int? = null,
    @SerializedName("gamesAdded") val gamesAdded: Int? = null,
    @SerializedName("newsAdded") val newsAdded: Int? = null,
    @SerializedName("articlesAdded") val articlesAdded: Int? = null,
    @SerializedName("journalsAdded") val journalsAdded: Int? = null,
    @SerializedName("thoughtsAdded") val thoughtsAdded: Int? = null,
    @SerializedName("country") val country: LocationResponse? = null,
    @SerializedName("city") val city: LocationResponse? = null,
)

fun UserResponse?.toModel() = User(
    teseraId = this?.teseraId ?: 0,
    login = this?.login.orEmpty(),
    name = this?.name.orEmpty(),
    comment = this?.comment.orEmpty(),
    avatarUrl = this?.avatarUrl.orEmpty(),
    gender = this?.gender.orEmpty(),
    rating = this?.rating ?: 0,
    experience = this?.experience ?: 0,
    creationDateUtc = this?.creationDateUtc.toDate(),
    modificationDateUtc = this?.modificationDateUtc.toDate(),
    collectionCount = this?.collectionCount ?: 0,
    gamesAdded = this?.gamesAdded ?: 0,
    newsAdded = this?.newsAdded ?: 0,
    articlesAdded = this?.articlesAdded ?: 0,
    journalsAdded = this?.journalsAdded ?: 0,
    thoughtsAdded = this?.thoughtsAdded ?: 0,
    country = this?.country.toModel(),
    city = this?.city.toModel()
)