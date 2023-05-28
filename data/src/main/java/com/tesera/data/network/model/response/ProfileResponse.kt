package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.Profile

data class ProfileResponse(
    @SerializedName("user") val user: UserResponse? = UserResponse(),
    @SerializedName("gamesInCollection") val gamesInCollection: Int? = null,
    @SerializedName("sales") val sales: List<MarketItemResponse> = emptyList(),
    @SerializedName("purchases") val purchases: List<MarketItemResponse> = emptyList(),
)

fun ProfileResponse?.toModel() = Profile(
    user = this?.user.toModel(),
    gamesInCollection = this?.gamesInCollection ?: 0,
    sales = this?.sales?.map { it.toModel() } ?: emptyList(),
    purchases = this?.purchases?.map { it.toModel() } ?: emptyList()
)
