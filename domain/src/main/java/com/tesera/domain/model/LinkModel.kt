package com.tesera.domain.model

data class LinkModel(
    val teseraId: Int,
    val objectType: String,
    val title: String,
    val photoUrl: String,
    val modificationDateUtc: String,
    val creationDateUtc: String,
    val author: AuthorModel,
)