package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.Author

data class AuthorResponse(
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("login") val login: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("avatarUrl") val avatarUrl: String? = null,
    @SerializedName("rating") val rating: Int? = null,
    @SerializedName("teseraUrl") val teseraUrl: String? = null,
)

fun AuthorResponse?.toAuthorModel() = Author(
    teseraId = this?.teseraId ?: 0,
    id = this?.id ?: 0,
    login = this?.login.orEmpty(),
    name = this?.name.orEmpty(),
    avatarUrl = this?.avatarUrl.orEmpty(),
    rating = this?.rating ?: 0,
    teseraUrl = this?.teseraUrl.orEmpty()
)