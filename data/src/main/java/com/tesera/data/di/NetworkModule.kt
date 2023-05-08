package com.tesera.data.di

import com.tesera.data.network.ApiService
import com.tesera.data.network.createApi
import com.tesera.data.storage.TeseraEncryptedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun getApiService(teseraPrefs: TeseraEncryptedPrefs): ApiService = createApi(teseraPrefs)
}