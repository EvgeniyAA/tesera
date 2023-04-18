package com.tesera.domain.model

data class GameDetailsModel(
    val game: GameModel,
    val ownersTotal: Int,
    val sellTotal: Int,
    val buyTotal: Int,
    val sellTotalAll: Int,
    val buyTotalAll: Int,
    val commentsTotal: Int,
    val reportsTotal: Int,
    val photosTotal: Int,
    val filesTotal: Int,
    val linksTotal: Int,
    val videoExternalTotal: Int,
    val videoInternalTotal: Int,
    val photos: List<PhotoModel>,
    val files: List<FileModel>,
    val links: List<LinkModel>,
    val similarGames: List<GameModel>,
    val relatedGames: List<GameModel>,
    val news: List<NewsModel>,
)