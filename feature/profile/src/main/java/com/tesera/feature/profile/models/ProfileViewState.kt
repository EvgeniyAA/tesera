package com.tesera.feature.profile.models

import androidx.compose.runtime.Stable
import com.tesera.core.mvi.UiState
import com.tesera.domain.model.Collections
import com.tesera.domain.model.Profile
import com.tesera.domain.model.UserReportsInfo

@Stable
data class ProfileViewState(
    val isLoading: Boolean = true,
    val data: ProfileData? = null,
    val error: Throwable? = null,
    val unauthorized: Boolean = false
) : UiState

@Stable
data class ProfileData(
    val profile: Profile,
    val collections: Collections,
    val reports: UserReportsInfo
)