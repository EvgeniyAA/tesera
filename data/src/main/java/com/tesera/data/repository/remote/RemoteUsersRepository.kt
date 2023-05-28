package com.tesera.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toModel
import com.tesera.domain.model.Collections
import com.tesera.domain.model.GameOwner
import com.tesera.domain.model.Profile
import com.tesera.domain.model.UserReportsInfo
import com.tesera.domain.users.UsersPageParams
import com.tesera.domain.users.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteUsersRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource,
) : UsersRepository {
    override fun getUsers(params: UsersPageParams): Flow<PagingData<GameOwner>> = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = { GameOwnerPagingSource(datasource, params) }
    ).flow

    override suspend fun getProfile(username: String): Profile = withContext(ioDispatcher) {
        datasource.profile(username).toModel()
    }

    override suspend fun getUserCollections(username: String): Collections =
        withContext(ioDispatcher) {
            datasource.userCollections(username).toModel()
        }

    override suspend fun getUserReports(username: String): UserReportsInfo =
        withContext(ioDispatcher) {
            datasource.userReports(username).toModel()
        }
}