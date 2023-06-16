package com.tesera.domain.model

import androidx.compose.runtime.Stable

@Stable
data class NewsInfo(
    val news: NewsModel,
    val author: Author,
)