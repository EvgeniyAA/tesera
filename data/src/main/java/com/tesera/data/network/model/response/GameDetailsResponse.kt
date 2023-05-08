package com.tesera.data.network.model.response

import com.tesera.domain.model.GameDetailsModel

data class GameDetailsResponse(
    val game: GameResponse? = GameResponse(),
    val ownersTotal: Int? = null,
    val sellTotal: Int? = null,
    val buyTotal: Int? = null,
    val sellTotalAll: Int? = null,
    val buyTotalAll: Int? = null,
    val commentsTotal: Int? = null,
    val reportsTotal: Int? = null,
    val photosTotal: Int? = null,
    val filesTotal: Int? = null,
    val linksTotal: Int? = null,
    val videoExternalTotal: Int? = null,
    val videoInternalTotal: Int? = null,
    val photos: List<PhotoResponse> = emptyList(),
    val files: List<FileResponse> = emptyList(),
    val links: List<LinkResponse> = emptyList(),
    val similars: List<GameResponse> = emptyList(),
    val relateds: List<GameResponse> = emptyList(),
    val news: List<NewsResponse> = emptyList(),
)

fun GameDetailsResponse?.toModel() = GameDetailsModel(
    game = this?.game.toGameModel(),
    ownersTotal = this?.ownersTotal ?: 0,
    sellTotal = this?.sellTotal ?: 0,
    buyTotal = this?.buyTotal ?: 0,
    sellTotalAll = this?.sellTotalAll ?: 0,
    buyTotalAll = this?.buyTotalAll ?: 0,
    commentsTotal = this?.commentsTotal ?: 0,
    reportsTotal = this?.reportsTotal ?: 0,
    photosTotal = this?.photosTotal ?: 0,
    filesTotal = this?.filesTotal ?: 0,
    linksTotal = this?.linksTotal ?: 0,
    videoExternalTotal = this?.videoExternalTotal ?: 0,
    videoInternalTotal = this?.videoInternalTotal ?: 0,
    photos = this?.photos?.map { it.toPhotoModel() } ?: emptyList(),
    files = this?.files?.map { it.toFileModel(this?.game?.alias) } ?: emptyList(),
    links = this?.links?.map { it.toLinkModel() } ?: emptyList(),
    similarGames = this?.similars?.map { it.toGameModel() } ?: emptyList(),
    relatedGames = this?.relateds?.map { it.toGameModel() } ?: emptyList(),
    news = this?.news?.map { it.toNewsModel() } ?: emptyList()
)