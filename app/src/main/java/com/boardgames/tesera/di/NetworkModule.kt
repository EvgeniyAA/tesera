package com.boardgames.tesera.di

import com.boardgames.tesera.data.TeseraApi
import com.boardgames.tesera.di.provider.ApiProvider
import com.boardgames.tesera.di.provider.OkHttpClientProvider
import okhttp3.OkHttpClient
import toothpick.config.Module

class NetworkModule(endpoint: String) : Module() {
    init {
        bind(String::class.java)
            .withName(ServerUrl::class.java)
            .toInstance(endpoint)
        bind(OkHttpClient::class.java)
            .withName(CustomOkHttpClient::class.java)
            .toProvider(OkHttpClientProvider::class.java)
            .providesSingleton()
        bind(TeseraApi::class.java)
            .toProvider(ApiProvider::class.java)
            .providesSingleton()
    }
}