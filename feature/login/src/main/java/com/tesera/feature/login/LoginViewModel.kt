package com.tesera.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tesera.core.extensions.TAG
import com.tesera.core.mvi.IntentHandler
import com.tesera.core.mvi.logInvalidIntent
import com.tesera.domain.authentication.AuthenticationState
import com.tesera.domain.authentication.AuthenticationUseCase
import com.tesera.feature.login.models.LoginIntent
import com.tesera.feature.login.models.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: AuthenticationUseCase
) : ViewModel(), IntentHandler<LoginIntent> {

    private val _loginViewState: MutableStateFlow<LoginViewState> =
        MutableStateFlow(LoginViewState.Loading)
    val loginViewState: StateFlow<LoginViewState> = _loginViewState

    override fun obtainIntent(intent: LoginIntent) =
        when (val currentState = _loginViewState.value) {
            is LoginViewState.Loading -> reduce(intent, currentState)
            is LoginViewState.Display -> reduce(intent, currentState)
            is LoginViewState.Error -> Unit
            LoginViewState.ToHome -> Unit
        }

    private fun reduce(intent: LoginIntent, currentState: LoginViewState.Loading) {
        when (intent) {
            LoginIntent.EnterScreen -> sendViewState(LoginViewState.Display)
            else -> TAG.logInvalidIntent(intent, currentState)
        }
    }

    private fun reduce(intent: LoginIntent, currentState: LoginViewState.Display) {
        when (intent) {
            is LoginIntent.LoginClick -> {
                sendViewState(LoginViewState.Loading)
                viewModelScope.launch {
                    useCase.authWithPassword(
                        intent.username.concatToString(),
                        intent.password.concatToString()
                    ).collect {
                        when (it) {
                            is AuthenticationState.Error -> sendViewState(LoginViewState.Error(it.error))
                            AuthenticationState.Success -> sendViewState(LoginViewState.ToHome)
                        }
                    }
                }
            }
            else -> TAG.logInvalidIntent(intent, currentState)
        }
    }

    private fun sendViewState(viewState: LoginViewState) {
        viewModelScope.launch {
            _loginViewState.emit(viewState)
        }
    }
}