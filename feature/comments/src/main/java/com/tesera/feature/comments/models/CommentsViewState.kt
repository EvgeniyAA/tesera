package com.tesera.feature.comments.models

import com.tesera.domain.model.CommentModel

data class CommentsViewState(
    val comments: List<CommentModel> = emptyList(),
    val isLoading: Boolean= true,
    val action: CommentsAction = CommentsAction.None,
)

sealed class CommentsAction {
    object None : CommentsAction()
}