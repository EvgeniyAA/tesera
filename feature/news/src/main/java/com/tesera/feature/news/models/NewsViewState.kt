package com.tesera.feature.news.models

import com.tesera.domain.model.NewsPreviewModel

data class NewsViewState(
    val action: NewsAction = NewsAction.None
)

sealed class NewsAction {
    object None : NewsAction()
    data class ToNewsDetails(val news: NewsPreviewModel) : NewsAction()
}