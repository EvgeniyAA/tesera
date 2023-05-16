package com.tesera.domain.comments

import com.tesera.domain.model.CommentModel
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    suspend fun expandComment(id: Int)
    suspend fun getComments(
        objecttype: String,
        alias: String,
        lastCommentId: Int,
        limit: Int?,
    )

    val comments: Flow<List<CommentModel>>
}