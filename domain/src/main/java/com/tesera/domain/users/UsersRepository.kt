package com.tesera.domain.users

import androidx.paging.PagingData
import com.tesera.domain.model.Collections
import com.tesera.domain.model.GameOwner
import com.tesera.domain.model.Profile
import com.tesera.domain.model.UserReportsInfo
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers(params: UsersPageParams): Flow<PagingData<GameOwner>>
    suspend fun getProfile(username: String): Profile
    suspend fun getUserCollections(username: String): Collections
    suspend fun getUserReports(username: String): UserReportsInfo
}