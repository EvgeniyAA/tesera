package com.tesera.domain.news

import com.tesera.domain.model.NewsInfo
import com.tesera.domain.model.NewsModel

interface NewsDetailsRepository {
    suspend fun getNews(alias: String): NewsInfo
    suspend fun getThoughts(alias: String): NewsInfo
    suspend fun getArticles(alias: String): NewsInfo
    suspend fun getJournals(alias: String): NewsInfo
}