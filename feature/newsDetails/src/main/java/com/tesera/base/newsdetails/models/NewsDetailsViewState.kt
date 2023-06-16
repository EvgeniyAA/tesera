package com.tesera.base.newsdetails.models

import androidx.compose.runtime.Stable
import com.tesera.core.mvi.UiState
import com.tesera.domain.model.NewsInfo

@Stable
data class NewsDetailsViewState(
    val isLoading: Boolean = true,
    val newsDetails: NewsInfo? = null
): UiState
