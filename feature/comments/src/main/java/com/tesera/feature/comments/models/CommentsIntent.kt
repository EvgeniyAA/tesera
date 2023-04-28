package com.tesera.feature.comments.models

sealed class CommentsIntent {
    data class GetComments(
        val alias: String,
        val objectType: String,
        val limit: Int = 5,
        val lastCommendId: Int = 0,
    ) : CommentsIntent()

    object ActionInvoked : CommentsIntent()
    data class CommentExpanded(val id: Int): CommentsIntent()
    data class CommentLiked(val id: Int): CommentsIntent()
}