package com.tesera.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tesera.core.constants.KEY_USER
import com.tesera.core.mvi.MviViewModel
import com.tesera.core.mvi.Reducer
import com.tesera.domain.profile.ProfileUseCase
import com.tesera.feature.profile.models.ProfileData
import com.tesera.feature.profile.models.ProfileIntent
import com.tesera.feature.profile.models.ProfileViewState
import com.tesera.feature.profile.models.mapToIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase,
    private val savedStateHandle: SavedStateHandle,
) : MviViewModel<ProfileViewState, ProfileIntent>() {
    private val reducer = ProfileReducer(ProfileViewState())

    override val state: StateFlow<ProfileViewState>
        get() = reducer.state

    init {
        viewModelScope.launch {
            val username = savedStateHandle.get<String>(KEY_USER)
            when {
                username.isNullOrEmpty() -> obtainIntent(ProfileIntent.UiActions.Unauthorized)
                else -> useCase.getProfile(username).collect {
                    obtainIntent(it.mapToIntent())
                }
            }

        }
    }

    override fun obtainIntent(intent: ProfileIntent) {
        reducer.sendIntent(intent)
    }

    private inner class ProfileReducer(initial: ProfileViewState) :
        Reducer<ProfileViewState, ProfileIntent>(initial) {
        override fun reduce(oldState: ProfileViewState, intent: ProfileIntent) {
            Timber.d(intent.toString())
            when (intent) {
                is ProfileIntent.PartialState.Error ->
                    setState(oldState.copy(isLoading = false, error = intent.error, data = null))

                ProfileIntent.PartialState.Loading ->
                    setState(oldState.copy(isLoading = true, error = null, data = null))

                is ProfileIntent.PartialState.Result ->
                    setState(
                        oldState.copy(
                            data = ProfileData(intent.profile, intent.collections, intent.reports),
                            isLoading = false,
                            error = null
                        )
                    )

                ProfileIntent.UiActions.Unauthorized -> setState(
                    oldState.copy(
                        unauthorized = true,
                        isLoading = false
                    )
                )
            }
        }
    }
}