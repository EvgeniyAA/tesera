package com.tesera.domain.news

import androidx.paging.PagingData
import com.tesera.domain.model.NewsPreviewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    fun getLatestNews(): Flow<List<NewsPreviewModel>> =
        newsRepository.getLatestNews(NewsPageParams())

    fun getNews(): Flow<PagingData<NewsPreviewModel>> = newsRepository.getNews(NewsPageParams())
}