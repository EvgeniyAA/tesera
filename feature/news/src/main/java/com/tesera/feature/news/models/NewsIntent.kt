package com.tesera.feature.news.models

import com.tesera.domain.model.NewsPreviewModel

sealed class NewsIntent {
    data class NewsDetailsClicked(val news: NewsPreviewModel) : NewsIntent()
    object ActionInvoked : NewsIntent()
}