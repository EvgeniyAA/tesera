package com.boardgames.tesera.di.provider

import com.boardgames.tesera.data.TeseraApi
import com.boardgames.tesera.di.CustomOkHttpClient
import com.boardgames.tesera.di.ServerUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class ApiProvider @Inject constructor(
    @CustomOkHttpClient private val client: OkHttpClient,
    @ServerUrl private val url: String
) : Provider<TeseraApi> {
    override fun get(): TeseraApi = with(Retrofit.Builder()) {
        addConverterFactory(MoshiConverterFactory.create())
        client(client)
        baseUrl(url)
        build()
    }.create(TeseraApi::class.java)
}