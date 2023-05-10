package com.tesera.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class NewsPreviewModel(
    val objectId: Int,
    val objectType: NewsType,
    val title: String,
    val title2: String,
    val alias: String,
    val contentShort: String,
    val creationDateUtc: Date,
    val photoUrl: String,
    val rating: Double,
    val commentsTotal: Int,
    val numVotes: Int,
    val authorModel: AuthorModel,
)

enum class NewsType {
    @SerializedName("News")
    News,
    @SerializedName("Article")
    Article,
    @SerializedName("Thought")
    Thought,
    @SerializedName("Journal")
    Journal,
    None
}