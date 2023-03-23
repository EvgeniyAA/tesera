package com.tesera.data.network.model.response

import com.tesera.data.model.AuthModel

data class AuthResponse(
    val id: String?,
    val token: String?,
    val expiresMin: Int?
)

fun AuthResponse?.toModel(): AuthModel =
    AuthModel(
        id = this?.id.orEmpty(),
        token = this?.token.orEmpty(),
        expiresMin = this?.expiresMin ?: 0
    )

