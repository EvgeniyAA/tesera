package com.tesera.feature.users

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tesera.core.constants.KEY_ALIAS
import com.tesera.domain.model.GameOwner
import com.tesera.domain.users.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: UsersUseCase,
) : ViewModel() {
    private val alias = savedStateHandle.get<String>(KEY_ALIAS) ?: ""

    val owners: Flow<PagingData<GameOwner>> = useCase.getUsers(alias).cachedIn(viewModelScope)
}