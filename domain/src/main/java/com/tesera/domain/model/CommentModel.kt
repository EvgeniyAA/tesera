package com.tesera.domain.model

import androidx.compose.runtime.Stable
import java.util.Date

@Stable
data class CommentModel(
    val teseraId: Int,
    val id: Int,
    val parentId: Int?,
    val title: String?,
    val content: String,
    val rating: Int,
    val creationDateUtc: Date,
    val author: Author,
//    val teseraObject: TeseraObjectModel,
    val isExpanded: Boolean = false,
    val isLiked: Boolean = false
)