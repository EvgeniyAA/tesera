package com.tesera.data.repository.remote

import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.comments.CommentsRepository
import com.tesera.domain.model.CommentModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
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
        withContext(ioDispatcher) {
            cachedComments =
                cachedComments.map { it.copy(isExpanded = if (id == it.id) !it.isExpanded else it.isExpanded) }
            _comments.emit(cachedComments)
        }
    }

    override suspend fun getComments(
        objecttype: String,
        alias: String,
        lastCommentId: Int,
        limit: Int?,
    ) {
        val result = datasource.getComments(objecttype, alias, lastCommentId, limit)
        cachedComments = result.map { it.toModel() }
        _comments.emit(cachedComments)
    }
}