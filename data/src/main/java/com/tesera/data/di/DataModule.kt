package com.tesera.data.di

import com.tesera.data.network.ApiService
import com.tesera.data.network.createApi
import com.tesera.data.repository.local.LocalGamesFilterRepository
import com.tesera.data.repository.remote.*
import com.tesera.data.storage.TeseraEncryptedPrefs
import com.tesera.data.storage.TeseraPrefs
import com.tesera.domain.authentication.LoginRepository
import com.tesera.domain.comments.CommentsRepository
import com.tesera.domain.gameDetails.GameDetailsRepository
import com.tesera.domain.games.GamesRepository
import com.tesera.domain.games.filters.GamesFilterRepository
import com.tesera.domain.news.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsLoginRepository(loginRepository: RemoteLoginRepository): LoginRepository

    @Binds
    fun bindsGamesRepository(gamesRepository: RemoteGameRepository): GamesRepository

    @Binds
    fun bindsGameDetailsRepository(gameDetailsRepository: RemoteGameDetailsRepository): GameDetailsRepository

    @Binds
    fun bindsCommentsRepository(commentsRepository: RemoteCommentsRepository): CommentsRepository

    @Binds
    fun bindsNewsRepository(newsRepository: RemoteNewsRepository): NewsRepository

    @Binds
    @Singleton
    fun bindsGamesFilterRepository(gamesFilterRepository: LocalGamesFilterRepository): GamesFilterRepository

    @Binds
    fun bindsTeseraPrefs(teseraPrefs: TeseraEncryptedPrefs): TeseraPrefs
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun getApiService(teseraPrefs: TeseraEncryptedPrefs): ApiService = createApi(teseraPrefs)
}