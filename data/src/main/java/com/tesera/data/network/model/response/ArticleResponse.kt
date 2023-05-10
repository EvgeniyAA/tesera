package com.tesera.data.network.model.response

import com.tesera.domain.model.NewsInfo

data class ArticleResponse(
    val article: NewsResponse,
    val author: AuthorResponse
)

fun ArticleResponse?.toModel() = NewsInfo(
    news = this?.article.toNewsModel(),
    author = this?.author.toAuthorModel()
)