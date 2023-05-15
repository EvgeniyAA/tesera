package com.tesera.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Author(
    val teseraId: Int,
    val id: Int,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val rating: Int,
    val teseraUrl: String,
)
