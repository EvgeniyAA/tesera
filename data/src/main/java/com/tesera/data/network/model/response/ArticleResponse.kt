package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.NewsInfo

data class ArticleResponse(
    @SerializedName("article") val article: NewsResponse,
    @SerializedName("author") val author: AuthorResponse
)

fun ArticleResponse?.toModel() = NewsInfo(
    news = this?.article.toNewsModel(),
    author = this?.author.toAuthorModel()
)