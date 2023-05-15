package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.NewsInfo

data class ThoughtResponse(
    @SerializedName("thought") val thought: NewsResponse,
    @SerializedName("author") val author: AuthorResponse
)

fun ThoughtResponse?.toModel() = NewsInfo(
    news = this?.thought.toNewsModel(),
    author = this?.author.toAuthorModel()
)