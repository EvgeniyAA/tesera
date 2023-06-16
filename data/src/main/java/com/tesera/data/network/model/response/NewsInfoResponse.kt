package com.tesera.data.network.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.NewsInfo

@Keep
data class NewsInfoResponse(
    @SerializedName("news") val news: NewsResponse,
    @SerializedName("author") val author: AuthorResponse,
)

fun NewsInfoResponse?.toModel() = NewsInfo(
    news = this?.news.toNewsModel(),
    author = this?.author.toAuthorModel()
)