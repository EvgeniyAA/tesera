package com.tesera.data.di

import android.content.Context
import com.tesera.core.model.ContextWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContextWorker(@ApplicationContext appContext: Context): ContextWorker {
        return ContextWorker(appContext)
    }
}