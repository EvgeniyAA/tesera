package com.tesera.data.storage.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tesera.data.storage.db.Converters
import com.tesera.domain.model.*

@Entity(tableName = "gameDetails")
@TypeConverters(Converters::class)
data class GameDetailsEntity(
    @PrimaryKey val id: Int,
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

fun GameDetails.toEntity() = GameDetailsEntity(
    id = this.id,
    game = this.game,
    ownersTotal = this.ownersTotal,
    sellTotal = this.sellTotal,
    buyTotal = this.buyTotal,
    sellTotalAll = this.sellTotalAll,
    buyTotalAll = this.buyTotalAll,
    commentsTotal = this.commentsTotal,
    reportsTotal = this.reportsTotal,
    photosTotal = this.photosTotal,
    filesTotal = this.filesTotal,
    linksTotal = this.linksTotal,
    videoExternalTotal = this.videoExternalTotal,
    videoInternalTotal = this.videoInternalTotal,
    photos = this.photos,
    gameFiles = this.gameFiles,
    links = this.links,
    similarGames = this.similarGames,
    relatedGames = this.relatedGames,
    news = this.news
)