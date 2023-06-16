package com.tesera.data.network.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.NewsInfo

@Keep
data class ThoughtResponse(
    @SerializedName("thought") val thought: NewsResponse,
    @SerializedName("author") val author: AuthorResponse
)

fun ThoughtResponse?.toModel() = NewsInfo(
    news = this?.thought.toNewsModel(),
    author = this?.author.toAuthorModel()
)