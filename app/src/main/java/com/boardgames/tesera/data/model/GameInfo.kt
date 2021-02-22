package com.boardgames.tesera.data.model

import com.squareup.moshi.Json

data class GameInfo(
    val teseraId: Long,
    val bggId: Long?,
    val title: String?,
    val title2: String?,
    val alias: String?,
    val descriptionShort: String?,
    val htmlDescription: String?,
    val photoUrl: String?,
    val year: Int,
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