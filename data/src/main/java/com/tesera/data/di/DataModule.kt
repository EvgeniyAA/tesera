package com.tesera.data.di

import android.content.Context
import androidx.room.Room
import com.tesera.data.repository.remote.*
import com.tesera.data.storage.TeseraEncryptedPrefs
import com.tesera.data.storage.TeseraPrefs
import com.tesera.data.storage.db.AppDatabase
import com.tesera.data.storage.db.dao.FileDao
import com.tesera.domain.authentication.LoginRepository
import com.tesera.domain.comments.CommentsRepository
import com.tesera.domain.gameDetails.GameDetailsRepository
import com.tesera.domain.games.GamesRepository
import com.tesera.domain.media.MediaRepository
import com.tesera.domain.news.NewsDetailsRepository
import com.tesera.domain.news.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun bindsMediaRepository(mediaRepository: RemoteMediaRepository): MediaRepository

    @Binds
    fun bindsNewsDetailsRepository(newsDetailsRepository: RemoteNewsDetailsRepository): NewsDetailsRepository
//    @Binds
//    @Singleton
//    fun bindsGamesFilterRepository(gamesFilterRepository: LocalGamesFilterRepository): GamesFilterRepository

    @Binds
    fun bindsTeseraPrefs(teseraPrefs: TeseraEncryptedPrefs): TeseraPrefs

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "app_database").build()

        @Provides
        @Singleton
        fun providesFileDao(database: AppDatabase): FileDao = database.fileDao()
    }
}