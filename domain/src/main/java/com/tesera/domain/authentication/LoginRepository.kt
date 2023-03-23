package com.tesera.domain.authentication

import kotlinx.coroutines.flow.Flow

interface LoginRepository {
     fun login(username: String, password: String): Flow<AuthenticationState>
     fun isSessionValid(): Flow<AuthenticationState>
}