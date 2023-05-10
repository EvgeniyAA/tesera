package com.tesera.data.network.model.response

import com.tesera.domain.model.NewsInfo

data class ThoughtResponse(
    val thought: NewsResponse,
    val author: AuthorResponse
)

fun ThoughtResponse?.toModel() = NewsInfo(
    news = this?.thought.toNewsModel(),
    author = this?.author.toAuthorModel()
)