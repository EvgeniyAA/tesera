package com.boardgames.tesera.data.response

import com.boardgames.tesera.data.model.GameInfo
import com.boardgames.tesera.extensions.orDefault
import com.squareup.moshi.Json

data class GameInfoResponse(
    val teseraId: Long?,
    val bggId: Long?,
    val title: String?,
    val title2: String?,
    val alias: String?,
    val descriptionShort: String?,
    @field:Json(name = "description") val htmlDescription: String?,
    val photoUrl: String?,
    val year: Int?,
    val ratingUser: Double?,
    val n10Rating: Double?,
    val n20Rating: Double?,
    val bggRating: Double?,
    val bggGeekRating: Double?,
    val bggNumVotes: Int?,
    val numVotes: Int?,
    val playersMin: Int?,
    val playersMax: Int?,
    val playersMinRecommend: Int?,
    val playersMaxRecommend: Int?,
    val playersAgeMin: Int?,
    val timeToLearn: Int?,
    val playtimeMin: Int?,
    val playtimeMax: Int?,
    val commentsTotal: Int?,
    val commentsTotalNew: Int?
)

fun GameInfoResponse?.toModel() = GameInfo(
    teseraId = this?.teseraId.orDefault(),
    bggId = this?.bggId.orDefault(),
    title = this?.title.orDefault(),
    title2 = this?.title2.orDefault(),
    alias = this?.alias.orDefault(),
    descriptionShort = this?.descriptionShort.orDefault(),
    htmlDescription = this?.htmlDescription.orDefault(),
    photoUrl = this?.photoUrl.orDefault(),
    year = this?.year.orDefault(),
    ratingUser = this?.ratingUser.orDefault(),
    n10Rating = this?.n10Rating.orDefault(),
    n20Rating = this?.n20Rating.orDefault(),
    bggRating = this?.bggRating.orDefault(),
    bggGeekRating = this?.bggGeekRating.orDefault(),
    bggNumVotes = this?.bggNumVotes.orDefault(),
    numVotes = this?.numVotes.orDefault(),
    playersMin = this?.playersMin.orDefault(),
    playersMax = this?.playersMax.orDefault(),
    playersMinRecommend = this?.playersMinRecommend,
    playersMaxRecommend = this?.playersMaxRecommend,
    playersAgeMin = this?.playersAgeMin.orDefault(),
    timeToLearn = this?.timeToLearn,
    playtimeMin = this?.playtimeMin,
    playtimeMax = this?.playtimeMax,
    commentsTotal = this?.commentsTotal,
    commentsTotalNew = this?.commentsTotalNew
)