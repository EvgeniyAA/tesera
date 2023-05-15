package com.tesera.domain.model

data class GameDetails(
    val id: Int,
    val game: Game,
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
    val photos: List<Photo>,
    val gameFiles: List<GameFile>,
    val links: List<Link>,
    val similarGames: List<Game>,
    val relatedGames: List<Game>,
    val news: List<NewsPreview>,
)