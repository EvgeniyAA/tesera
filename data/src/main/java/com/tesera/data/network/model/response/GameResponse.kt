package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.GameModel
import com.tesera.domain.model.GamePreviewModel

data class GameResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("teseraId") val teseraId: Int? = null,
    @SerializedName("bggId") val bggId: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("title2") val title2: String? = null,
    @SerializedName("alias") val alias: String? = null,
    @SerializedName("descriptionShort") val descriptionShort: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("modificationDateUtc") val modificationDateUtc: String? = null,
    @SerializedName("creationDateUtc") val creationDateUtc: String? = null,
    @SerializedName("photoUrl") val photoUrl: String? = null,
    @SerializedName("year") val year: Int? = null,
    @SerializedName("ratingUser") val ratingUser: Double? = null,
    @SerializedName("n10Rating") val n10Rating: Double? = null,
    @SerializedName("n20Rating") val n20Rating: Double? = null,
    @SerializedName("bggRating") val bggRating: Double? = null,
    @SerializedName("bggGeekRating") val bggGeekRating: Double? = null,
    @SerializedName("bggNumVotes") val bggNumVotes: Int? = null,
    @SerializedName("numVotes") val numVotes: Int? = null,
    @SerializedName("playersMin") val playersMin: Int? = null,
    @SerializedName("playersMax") val playersMax: Int? = null,
    @SerializedName("playersMinRecommend") val playersMinRecommend: Int? = null,
    @SerializedName("playersMaxRecommend") val playersMaxRecommend: Int? = null,
    @SerializedName("playersAgeMin") val playersAgeMin: Int? = null,
    @SerializedName("timeToLearn") val timeToLearn: Int? = null,
    @SerializedName("playtimeMin") val playtimeMin: Int? = null,
    @SerializedName("playtimeMax") val playtimeMax: Int? = null,
    @SerializedName("commentsTotal") val commentsTotal: Int? = null,
    @SerializedName("commentsTotalNew") val commentsTotalNew: Int? = null,
    @SerializedName("isAddition") val isAddition: Boolean? = null,
)

fun GameResponse?.toGamePreviewModel() = GamePreviewModel(
    id = this?.id ?: 0,
    bggId = this?.bggId ?: 0,
    title = this?.title.orEmpty(),
    title2 = this?.title2.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    year = this?.year ?: 0,
    commentsTotalNew = this?.commentsTotalNew ?: 0,
    commentsTotal = this?.commentsTotal ?: 0,
    n10Rating = this?.n10Rating ?: 0.0,
    alias = this?.alias.orEmpty()
)

fun GameResponse?.toGameModel() = GameModel(
    id = this?.id ?: 0,
    teseraId = this?.teseraId ?: 0,
    bggId = this?.bggId ?: 0,
    title = this?.title.orEmpty(),
    title2 = this?.title2.orEmpty(),
    alias = this?.alias.orEmpty(),
    descriptionShort = this?.descriptionShort.orEmpty(),
    description = this?.description.orEmpty(),
    modificationDateUtc = this?.modificationDateUtc.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    year = this?.year ?: 0,
    ratingUser = this?.ratingUser ?: 0.0,
    n10Rating = this?.n10Rating ?: 0.0,
    n20Rating = this?.n20Rating ?: 0.0,
    bggRating = this?.bggRating ?: 0.0,
    bggGeekRating = this?.bggGeekRating ?: 0.0,
    bggNumVotes = this?.bggNumVotes ?: 0,
    numVotes = this?.numVotes ?: 0,
    playersMin = this?.playersMin ?: 0,
    playersMax = this?.playersMax ?: 0,
    playersMinRecommend = this?.playersMinRecommend ?: 0,
    playersMaxRecommend = this?.playersMaxRecommend ?: 0,
    playersAgeMin = this?.playersAgeMin ?: 0,
    timeToLearn = this?.timeToLearn ?: 0,
    playtimeMin = this?.playtimeMin ?: 0,
    playtimeMax = this?.playtimeMax ?: 0,
    commentsTotal = this?.commentsTotal ?: 0,
    commentsTotalNew = this?.commentsTotalNew ?: 0,
    isAddition = this?.isAddition ?: false
)
