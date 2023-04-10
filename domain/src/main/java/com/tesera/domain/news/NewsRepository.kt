package com.tesera.domain.news

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews (params: NewsPageParams): Flow<PagingData<NewsPreviewModel>>
    fun getLatestNews (params: NewsPageParams): Flow<List<NewsPreviewModel>>
}