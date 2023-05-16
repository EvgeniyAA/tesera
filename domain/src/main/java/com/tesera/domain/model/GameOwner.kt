package com.tesera.domain.model

import androidx.compose.runtime.Stable
import java.util.Date

@Stable
data class GameOwner(
    val teseraId: Int,
    val login: String,
    val name: String,
    val rating: Double,
    val comment: String,
    val eventDate: Date?,
    val avatarUrl: String?
)