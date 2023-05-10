package com.tesera.data.network.model.response

import com.tesera.domain.model.NewsInfo

data class NewsInfoResponse(
    val news: NewsResponse,
    val author: AuthorResponse,
)

fun NewsInfoResponse?.toModel() = NewsInfo(
    news = this?.news.toNewsModel(),
    author = this?.author.toAuthorModel()
)