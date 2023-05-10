package com.tesera.base.newsdetails.models

import com.tesera.core.mvi.UiState
import com.tesera.domain.model.NewsInfo

data class NewsDetailsViewState(
    val isLoading: Boolean = true,
    val newsDetails: NewsInfo? = null
): UiState
