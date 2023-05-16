package com.tesera.domain.users

import androidx.paging.PagingData
import com.tesera.domain.model.GameOwner
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers(params: UsersPageParams): Flow<PagingData<GameOwner>>
}