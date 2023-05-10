package com.tesera.domain.news

import com.tesera.domain.model.NewsType
import javax.inject.Inject

class NewsDetailsUseCase @Inject constructor(
    private val newsDetailsRepository: NewsDetailsRepository,
) {

    suspend fun getNewsDetails(newsType: NewsType, alias: String) = when (newsType) {
        NewsType.News -> newsDetailsRepository.getNews(alias)
        NewsType.Article -> newsDetailsRepository.getArticles(alias)
        NewsType.Thought -> newsDetailsRepository.getThoughts(alias)
        NewsType.Journal -> newsDetailsRepository.getJournals(alias)
        NewsType.None -> throw java.lang.IllegalArgumentException("unknown type of newsType: ${newsType.name}")
    }
}