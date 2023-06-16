package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.GameDetails
import com.tesera.domain.model.NewsType

data class GameDetailsResponse(
    @SerializedName("game") val game: GameResponse? = GameResponse(),
    @SerializedName("ownersTotal") val ownersTotal: Int? = null,
    @SerializedName("sellTotal") val sellTotal: Int? = null,
    @SerializedName("buyTotal") val buyTotal: Int? = null,
    @SerializedName("sellTotalAll") val sellTotalAll: Int? = null,
    @SerializedName("buyTotalAll") val buyTotalAll: Int? = null,
    @SerializedName("commentsTotal") val commentsTotal: Int? = null,
    @SerializedName("reportsTotal") val reportsTotal: Int? = null,
    @SerializedName("photosTotal") val photosTotal: Int? = null,
    @SerializedName("filesTotal") val filesTotal: Int? = null,
    @SerializedName("linksTotal") val linksTotal: Int? = null,
    @SerializedName("videoExternalTotal") val videoExternalTotal: Int? = null,
    @SerializedName("videoInternalTotal") val videoInternalTotal: Int? = null,
    @SerializedName("photos") val photos: List<PhotoResponse> = emptyList(),
    @SerializedName("files") val files: List<FileResponse> = emptyList(),
    @SerializedName("links") val links: List<LinkResponse> = emptyList(),
    @SerializedName("similars") val similars: List<GameResponse> = emptyList(),
    @SerializedName("relateds") val relateds: List<GameResponse> = emptyList(),
    @SerializedName("news") val news: List<NewsPreviewResponse> = emptyList(),
)

fun GameDetailsResponse?.toModel() = GameDetails(
    id = this?.game?.id ?: 0,
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
    gameFiles = this?.files?.map { it.toFileModel(this.game?.alias) } ?: emptyList(),
    links = this?.links?.map { it.toLinkModel() } ?: emptyList(),
    similarGames = this?.similars?.map { it.toGameModel() } ?: emptyList(),
    relatedGames = this?.relateds?.map { it.toGameModel() } ?: emptyList(),
    news = this?.news?.map { it.toModel().copy(objectType = NewsType.News) } ?: emptyList()
)