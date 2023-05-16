package com.tesera.domain.users

import androidx.paging.PagingData
import com.tesera.domain.model.GameOwner
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
) {
    fun getUsers(alias: String): Flow<PagingData<GameOwner>> =
        usersRepository.getUsers(UsersPageParams(alias = alias))
}