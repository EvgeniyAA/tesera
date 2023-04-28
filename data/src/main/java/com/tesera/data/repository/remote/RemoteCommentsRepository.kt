package com.tesera.data.repository.remote

import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.comments.CommentsPartialState
import com.tesera.domain.comments.CommentsRepository
import com.tesera.domain.model.CommentModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteCommentsRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource,
) : CommentsRepository {

    private var cachedComments = emptyList<CommentModel>()
    private val _comments = MutableStateFlow<List<CommentModel>>(emptyList())

    override val comments
        get() = _comments

    override suspend fun expandComment(id: Int) {
        cachedComments =
            cachedComments.map { it.copy(isExpanded = if (id == it.id) !it.isExpanded else it.isExpanded) }
        withContext(ioDispatcher) {
            _comments.emit(cachedComments)
        }
    }

    override suspend fun getComments(
        objecttype: String,
        alias: String,
        lastCommentId: Int,
        limit: Int?,
    ): Flow<CommentsPartialState> = flow {
        emit(CommentsPartialState.IsLoading)
        datasource.getComments(objecttype, alias, lastCommentId, limit)
            .onSuccess { commentsResponse ->
                emit(CommentsPartialState.Success)
                cachedComments = commentsResponse.map { it.toModel() }
                _comments.emit(cachedComments)
            }
            .onFailure { emit(CommentsPartialState.Error(it)) }
    }
}