package com.tesera.domain.model

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import java.util.*

@Stable
data class NewsPreview(
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
    val author: Author,
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