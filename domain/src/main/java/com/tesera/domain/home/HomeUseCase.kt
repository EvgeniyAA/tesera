package com.tesera.domain.home

import com.tesera.domain.games.GamesRepository
import com.tesera.domain.games.filters.GamesFilter
import com.tesera.domain.games.filters.GamesType
import com.tesera.domain.news.NewsPageParams
import com.tesera.domain.news.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val gamesRepository: GamesRepository,
    private val newsRepository: NewsRepository,
) {
    suspend fun latestHotnessGames(): Flow<HomePartialState> = flow {
        emit(HomePartialState.GamesLoading.partial())
        try {
            val result = gamesRepository.getLatestGames(
                GamesFilter(type = GamesType.HOTNESS, limited = true)
            )
            emit(HomePartialState.Games(result))
        } catch (e: Exception) {
            emit(HomePartialState.GamesLoadingError(e))
        }
    }

    suspend fun latestNews(): Flow<HomePartialState> = flow {
        emit(HomePartialState.NewsLoading)
        try {
            val result = newsRepository.getLatestNews(NewsPageParams())
            emit(HomePartialState.News(result))
        } catch (e: Exception) {
            emit(HomePartialState.NewsLoadingError(e))
        }
    }

    suspend fun getContent() = merge(latestHotnessGames(), latestNews())

}