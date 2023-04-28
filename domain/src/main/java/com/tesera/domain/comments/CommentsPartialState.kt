package com.tesera.domain.comments

sealed class CommentsPartialState {
    object Success : CommentsPartialState()
    data class Error(val error: Throwable) : CommentsPartialState()
    object IsLoading : CommentsPartialState()
}