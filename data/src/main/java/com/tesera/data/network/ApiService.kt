package com.tesera.data.network

import com.tesera.data.network.model.request.AuthParams
import com.tesera.data.network.model.request.WakeUpParams
import com.tesera.data.network.model.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body params: AuthParams): Result<AuthResponse>

    @GET("auth/info")
    suspend fun authInfo(): Result<Any>
}