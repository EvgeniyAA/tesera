package com.tesera.data.network.model.response

import com.tesera.domain.model.AuthorModel

data class AuthorResponse(
    val teseraId: Int? = null,
    val id: Int? = null,
    val login: String? = null,
    val name: String? = null,
    val avatarUrl: String? = null,
    val rating: Int? = null,
    val teseraUrl: String? = null,
)

fun AuthorResponse?.toAuthorModel() = AuthorModel(
    teseraId = this?.teseraId ?: 0,
    id = this?.id ?: 0,
    login = this?.login.orEmpty(),
    name = this?.name.orEmpty(),
    avatarUrl = this?.avatarUrl.orEmpty(),
    rating = this?.rating ?: 0,
    teseraUrl = this?.teseraUrl.orEmpty()
)