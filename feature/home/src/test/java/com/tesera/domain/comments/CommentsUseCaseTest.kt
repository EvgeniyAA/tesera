package com.tesera.domain.comments

import com.tesera.domain.model.Author
import com.tesera.domain.model.CommentModel
import com.tesera.domain.model.TeseraObjectModel
import io.mockk.mockk
import org.junit.Test
import java.util.*

internal class CommentsUseCaseTest {

    private val repository: CommentsRepository = mockk(relaxed = true)

    @Test
    fun a() {
        val groupedItems = useCase().groupItemsByRootParent(items)
        println(groupedItems.size)
    }

    fun useCase() = CommentsUseCase(repository)

    companion object {
        val author = Author(0, 0, "", "", "", 0, "")
        val teseraObject = TeseraObjectModel(0, 0, 0, "", "", "", "")

        fun CommentModel(id: Int, parentId: Int?, date: Date) = CommentModel(
            teseraObject = teseraObject,
            author = author,
            title = "",
            content = "",
            rating = 0,
            creationDateUtc = date,
            teseraId = 0,
            id = id,
            parentId = parentId
        )
        val items = listOf(
            CommentModel(690403, 690314, Date(1650699889000)),
            CommentModel(690411, 690403, Date(1650704215000)),
            CommentModel(690415, 690411, Date(1650704586000)),
            CommentModel(690420, 690415, Date(1650705397000)),
            CommentModel(690423, 690420, Date(1650706321000)),
            CommentModel(690435, 690423, Date(1650730700000)),
            CommentModel(690441, 690435, Date(1650743429000)),
            CommentModel(690409, null, Date(1650701080000)),
            CommentModel(690413, 690409, Date(1650703259000)),
            CommentModel(690314, null, Date(1650622066000)),
        )
    }
}