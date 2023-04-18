package com.tesera.domain.model

data class AuthorModel(
    val teseraId: Int,
    val id: Int,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val rating: Int,
    val teseraUrl: String,
)
