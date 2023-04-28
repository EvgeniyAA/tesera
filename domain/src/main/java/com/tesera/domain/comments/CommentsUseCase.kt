package com.tesera.domain.comments

import com.tesera.domain.model.CommentModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CommentsUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository,
) {
    suspend fun expandComment(id: Int) = commentsRepository.expandComment(id)

    fun comments(): Flow<List<CommentModel>> = commentsRepository.comments.map { comments ->
        groupItemsByRootParent(comments).flatMap { it.value } }

    suspend fun getComments(
        objectType: String,
        alias: String,
        lastCommentId: Int,
        limit: Int?,
    ): Flow<CommentsPartialState> = commentsRepository.getComments(objectType, alias, lastCommentId, limit)


    fun groupItemsByRootParent(items: List<CommentModel>): Map<Int?, List<CommentModel>> {
        val itemMap = items.sortedBy { it.creationDateUtc }.associateBy { it.id }
        val rootGroups = mutableMapOf<Int?, MutableList<CommentModel>>()

        for (item in items) {
            var rootId: Int? = item.id
            while (itemMap.containsKey(rootId) && itemMap[rootId] != null && itemMap[rootId]?.parentId != null && itemMap[rootId]?.parentId != 0) {
                itemMap[rootId]?.let { rootId = it.parentId }
            }
            if (!rootGroups.containsKey(rootId)) {
                rootGroups[rootId] = mutableListOf()
            }
            rootGroups[rootId]?.add(item)
        }

        return rootGroups.mapValues { (_, group) -> group.sortedBy { it.creationDateUtc } }
    }
}

