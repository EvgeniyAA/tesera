package com.tesera.base.newsdetails.models

import com.tesera.core.mvi.UiIntent
import com.tesera.domain.model.NewsType

sealed class NewsDetailsIntent : UiIntent {
    data class GetNewsDetails(val type: NewsType, val alias: String) : NewsDetailsIntent()
}