package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.NewsInfo

data class NewsInfoResponse(
    @SerializedName("news") val news: NewsResponse,
    @SerializedName("author") val author: AuthorResponse,
)

fun NewsInfoResponse?.toModel() = NewsInfo(
    news = this?.news.toNewsModel(),
    author = this?.author.toAuthorModel()
)