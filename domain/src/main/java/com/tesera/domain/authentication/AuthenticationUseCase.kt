package com.tesera.domain.authentication

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    fun authWithPassword(username: String, password: String): Flow<AuthenticationState> =
        loginRepository.login(username, password)

    fun isSessionValid(): Flow<AuthenticationState> =
        loginRepository.isSessionValid()
}