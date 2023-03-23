package com.tesera.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesera.core.extensions.TAG
import com.tesera.domain.authentication.AuthenticationState
import com.tesera.domain.authentication.AuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: AuthenticationUseCase
) : ViewModel() {

    private val _splashViewState: MutableStateFlow<SplashViewState> =
        MutableStateFlow(SplashViewState.Display)
    val splashViewState: StateFlow<SplashViewState> = _splashViewState

    init {
        viewModelScope.launch {
            useCase.isSessionValid().collect {
                when (it) {
                    is AuthenticationState.Error -> _splashViewState.value = SplashViewState.ToLogin
                    AuthenticationState.Success -> _splashViewState.value = SplashViewState.ToHome
                }
            }
        }
    }
}