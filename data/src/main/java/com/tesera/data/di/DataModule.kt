package com.tesera.data.di

import com.tesera.data.network.ApiService
import com.tesera.data.network.createApi
import com.tesera.data.repository.remote.RemoteGameRepository
import com.tesera.domain.authentication.LoginRepository
import com.tesera.data.repository.remote.RemoteLoginRepository
import com.tesera.data.storage.TeseraEncryptedPrefs
import com.tesera.data.storage.TeseraPrefs
import com.tesera.domain.games.GamesRepository
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
    fun bindsTeseraPrefs(teseraPrefs: TeseraEncryptedPrefs): TeseraPrefs
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun getApiService(teseraPrefs: TeseraEncryptedPrefs): ApiService = createApi(teseraPrefs)
}