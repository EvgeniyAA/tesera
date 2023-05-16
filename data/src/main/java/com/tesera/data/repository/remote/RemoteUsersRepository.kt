package com.tesera.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.TeseraDispatchers
import com.tesera.domain.model.GameOwner
import com.tesera.domain.users.UsersPageParams
import com.tesera.domain.users.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteUsersRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource,
) : UsersRepository {
    override fun getUsers(params: UsersPageParams): Flow<PagingData<GameOwner>> = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = { GameOwnerPagingSource(datasource, params) }
    ).flow
}