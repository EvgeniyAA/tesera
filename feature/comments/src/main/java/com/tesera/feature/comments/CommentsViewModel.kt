package com.tesera.feature.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesera.core.constants.KEY_ALIAS
import com.tesera.core.constants.KEY_OBJECT_TYPE
import com.tesera.core.mvi.IntentHandler
import com.tesera.domain.comments.CommentsUseCase
import com.tesera.feature.comments.models.CommentsAction
import com.tesera.feature.comments.models.CommentsIntent
import com.tesera.feature.comments.models.CommentsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val commentsUseCase: CommentsUseCase,
) : ViewModel(), IntentHandler<CommentsIntent> {

    private val _commentsViewState: MutableStateFlow<CommentsViewState> =
        MutableStateFlow(CommentsViewState())
    val commentsViewState: StateFlow<CommentsViewState> = _commentsViewState

    init {
        viewModelScope.launch {
            val objectType = savedStateHandle.get<String>(KEY_OBJECT_TYPE) ?: ""
            val alias = savedStateHandle.get<String>(KEY_ALIAS) ?: ""
            getComments(objectType, alias, 0, 5)

            commentsUseCase.comments().collect {
                sendViewState(_commentsViewState.value.copy(comments = it))
            }
        }
    }

    override fun obtainIntent(intent: CommentsIntent): Unit = when (intent) {
        CommentsIntent.ActionInvoked -> {
            sendViewState(_commentsViewState.value.copy(action = CommentsAction.None))
        }

        is CommentsIntent.CommentExpanded -> expandComment(intent.id)
        is CommentsIntent.CommentLiked -> Unit
        CommentsIntent.GetMoreComments -> {}
    }

    private fun getComments(
        objectType: String,
        alias: String,
        lastCommentId: Int,
        limit: Int,
    ) {
        viewModelScope.launch {
            commentsUseCase.getComments(objectType, alias, lastCommentId, limit).collect()
        }
    }

    private fun expandComment(id: Int) {
        viewModelScope.launch { commentsUseCase.expandComment(id) }
    }

    private fun sendViewState(viewState: CommentsViewState) {
        viewModelScope.launch {
            _commentsViewState.emit(viewState)
        }
    }
}