package com.tesera.domain.model

data class CollectionItem(
    val collectionType: CollectionType,
    val gamesTotal: Int,
    val selfGamesCount: Int,
    val additionsCount: Int,
)