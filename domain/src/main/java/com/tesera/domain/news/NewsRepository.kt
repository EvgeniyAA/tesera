package com.tesera.domain.news

import androidx.paging.PagingData
import com.tesera.domain.model.NewsPreview
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews (params: NewsPageParams): Flow<PagingData<NewsPreview>>
    suspend fun getLatestNews (params: NewsPageParams): List<NewsPreview>
}