package com.tesera.domain.news

import androidx.paging.PagingData
import com.tesera.domain.model.NewsPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun getLatestNews(): List<NewsPreview> =
        newsRepository.getLatestNews(NewsPageParams())

    fun getNews(): Flow<PagingData<NewsPreview>> = newsRepository.getNews(NewsPageParams())
}