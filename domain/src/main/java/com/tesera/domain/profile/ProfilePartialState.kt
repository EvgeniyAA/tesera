package com.tesera.domain.profile

import com.tesera.domain.model.Collections
import com.tesera.domain.model.Profile
import com.tesera.domain.model.UserReportsInfo

sealed class ProfilePartialState {
    object Loading : ProfilePartialState()
    data class Result(
        val profile: Profile,
        val collections: Collections,
        val reports: UserReportsInfo,
    ) : ProfilePartialState()

    data class Error(val error: Throwable) : ProfilePartialState()
}