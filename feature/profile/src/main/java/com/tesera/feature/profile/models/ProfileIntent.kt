package com.tesera.feature.profile.models

import com.tesera.core.mvi.UiIntent
import com.tesera.domain.model.Collections
import com.tesera.domain.model.Profile
import com.tesera.domain.model.UserReportsInfo
import com.tesera.domain.profile.ProfilePartialState

sealed class ProfileIntent : UiIntent {
    sealed class UiActions : ProfileIntent() {
        object Unauthorized : ProfileIntent()
    }

    sealed class PartialState : ProfileIntent() {
        object Loading : PartialState()
        data class Result(
            val profile: Profile,
            val collections: Collections,
            val reports: UserReportsInfo,
        ) : PartialState()

        data class Error(val error: Throwable) : PartialState()
    }
}

fun ProfilePartialState.mapToIntent() = when (this) {
    is ProfilePartialState.Error -> ProfileIntent.PartialState.Error(this.error)
    ProfilePartialState.Loading -> ProfileIntent.PartialState.Loading
    is ProfilePartialState.Result -> ProfileIntent.PartialState.Result(
        this.profile,
        this.collections,
        this.reports
    )
}