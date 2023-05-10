package com.tesera.data.network.model.response

import com.tesera.domain.model.NewsInfo

data class JournalResponse(
    val journal: NewsResponse,
    val author: AuthorResponse
)

fun JournalResponse?.toModel() = NewsInfo(
    news = this?.journal.toNewsModel(),
    author = this?.author.toAuthorModel()
)