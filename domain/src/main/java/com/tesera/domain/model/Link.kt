package com.tesera.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Link(
    val teseraId: Int,
    val objectType: String,
    val title: String,
    val photoUrl: String,
    val modificationDateUtc: String,
    val creationDateUtc: String,
    val author: Author,
): MediaModel()