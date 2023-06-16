package com.tesera.data.network.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.tesera.domain.model.Profile

@Keep
data class ProfileResponse(
    @SerializedName("user") val user: UserResponse? = null,
    @SerializedName("photos") val photos: List<PhotoResponse>? = null,
    @SerializedName("gamesInCollection") val gamesInCollection: Int? = null,
    @SerializedName("sales") val sales: List<MarketItemResponse>? = null,
    @SerializedName("purchases") val purchases: List<MarketItemResponse>? = null,
)

fun ProfileResponse?.toModel() = Profile(
    user = this?.user.toModel(),
    gamesInCollection = this?.gamesInCollection ?: 0,
    sales = this?.sales?.map { it.toModel() } ?: emptyList(),
    purchases = this?.purchases?.map { it.toModel() } ?: emptyList(),
    photos = this?.photos?.map { it.toPhotoModel() } ?: emptyList()
    )
