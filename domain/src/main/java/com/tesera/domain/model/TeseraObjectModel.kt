package com.tesera.domain.model

data class TeseraObjectModel (
    val id: Int,
    val teseraId: Int,
    val commentsTotal: Int,
    val pictureUrl: String,
    val alias: String,
    val title: String,
    val objectType: String
)