package com.tesera.data.network.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.NewsInfo

@Keep

data class JournalResponse(
    @SerializedName("journal") val journal: NewsResponse,
    @SerializedName("author") val author: AuthorResponse
)

fun JournalResponse?.toModel() = NewsInfo(
    news = this?.journal.toNewsModel(),
    author = this?.author.toAuthorModel()
)