package com.boardgames.tesera.di.provider

import com.boardgames.tesera.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientProvider @Inject constructor() : Provider<OkHttpClient> {

    companion object {
        const val CONNECTION_TIMEOUT = 1000L * 10
        const val READ_TIMEOUT = 1000L * 20
    }

    override fun get(): OkHttpClient = with(OkHttpClient.Builder()) {
        connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        if (BuildConfig.DEBUG) {
            addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }
        build()
    }
}