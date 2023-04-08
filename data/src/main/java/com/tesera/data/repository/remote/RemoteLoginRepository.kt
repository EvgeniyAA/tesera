package com.tesera.data.repository.remote

import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers.IO
import com.tesera.data.network.model.response.toModel
import com.tesera.data.storage.TeseraPrefs
import com.tesera.domain.authentication.AuthenticationState
import com.tesera.domain.authentication.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteLoginRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val teseraPrefs: TeseraPrefs,
    private val datasource: NetworkDataSource
) : LoginRepository {

    override fun login(username: String, password: String): Flow<AuthenticationState> = flow {
        var result: AuthenticationState = AuthenticationState.Error("")
        datasource.login(username, password)
            .onSuccess { authResponse ->
                teseraPrefs.authToken = authResponse.toModel().token
                teseraPrefs.username = username
                result = AuthenticationState.Success
            }
            .onFailure { result = AuthenticationState.Error("") }
        emit(result)

    }.flowOn(ioDispatcher)

    override fun isSessionValid(): Flow<AuthenticationState> = flow {
            var result: AuthenticationState = AuthenticationState.Error("")
            datasource.authInfo()
                .onSuccess { authResponse ->
                    result = AuthenticationState.Success
                }
                .onFailure { result = AuthenticationState.Error("") }
            emit(result)
    }.flowOn(ioDispatcher)
}