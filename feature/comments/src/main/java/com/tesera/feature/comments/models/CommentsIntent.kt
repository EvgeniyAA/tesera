package com.tesera.feature.comments.models

sealed class CommentsIntent {
    object GetMoreComments : CommentsIntent()

    object ActionInvoked : CommentsIntent()
    data class CommentExpanded(val id: Int): CommentsIntent()
    data class CommentLiked(val id: Int): CommentsIntent()
}