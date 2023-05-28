package com.tesera.domain.profile

import com.tesera.domain.users.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
) {

    fun getProfile(username: String): Flow<ProfilePartialState> = flow {
        emit(ProfilePartialState.Loading)
        try {
            val profile = usersRepository.getProfile(username)
            val collections = usersRepository.getUserCollections(username)
            val reports = usersRepository.getUserReports(username)
            emit(ProfilePartialState.Result(profile, collections, reports))
        } catch (e: Exception) {
            emit(ProfilePartialState.Error(e))
        }
    }
}