package com.tesera.domain.model

data class FileModel(
    val teseraId: Int,
    val objectType: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val modificationDateUtc: String,
    val creationDateUtc: String,
    val author: AuthorModel,
)