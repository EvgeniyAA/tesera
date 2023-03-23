package com.tesera.data.model

data class AuthModel(
    val id: String,
    val token: String,
    val expiresMin: Int
)