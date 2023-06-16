package com.tesera.domain.model

import androidx.compose.runtime.Stable
import java.util.Date

@Stable
data class User(
    val teseraId: Int,
    val login: String,
    val name: String,
    val comment: String,
    val avatarUrl: String,
    val gender: String,
    val rating: Int,
    val experience: Int,
    val creationDateUtc: Date,
    val modificationDateUtc: Date,
    val collectionCount: Int,
    val gamesAdded: Int,
    val newsAdded: Int,
    val articlesAdded: Int,
    val journalsAdded: Int,
    val thoughtsAdded: Int,
    val country: Location,
    val city: Location,
)