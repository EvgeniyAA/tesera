package com.tesera.data.network

import com.tesera.core.BuildConfig
import com.tesera.data.storage.TeseraPrefs
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createApi(teseraPrefs: TeseraPrefs): ApiService {
    val endpoint = BuildConfig.ENDPOINT
    val retrofit = Retrofit.Builder()
        .baseUrl(endpoint)
        .client(createHttpClient(teseraPrefs))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .build()
    return retrofit.create(ApiService::class.java)
}

private fun createHttpClient(teseraPrefs: TeseraPrefs): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(Interceptor {
            if (teseraPrefs.authToken.isNotEmpty()) {
                val builder = it.request().newBuilder()
                builder.header("authorization", "Bearer ${teseraPrefs.authToken}")
                it.proceed(builder.build())
            } else it.proceed(it.request())
        })
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()