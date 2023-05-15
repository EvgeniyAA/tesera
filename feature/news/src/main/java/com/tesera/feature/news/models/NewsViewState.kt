package com.tesera.feature.news.models

import androidx.compose.runtime.Stable

@Stable
data class NewsViewState(
    val action: NewsAction = NewsAction.None,
)

sealed class NewsAction {
    object None : NewsAction()
}