package com.tesera.feature.login.models

import com.tesera.core.model.CredentialsField

sealed class LoginIntent {
    object EnterScreen : LoginIntent()
    object ReloadScreen : LoginIntent()
    data class LoginClick(
        val username: CredentialsField,
        val password: CredentialsField
    ) : LoginIntent()
}