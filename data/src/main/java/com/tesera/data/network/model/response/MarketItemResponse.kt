package com.tesera.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.tesera.core.utils.toDate
import com.tesera.domain.model.ConditionType
import com.tesera.domain.model.MarketGameItem
import com.tesera.domain.model.MarketItemType
import java.util.Date

data class MarketItemResponse(
    @SerializedName("type") val type: MarketItemType? = null,
    @SerializedName("value") val value: String? = null,
    @SerializedName("alias") val alias: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("relationId") val relationId: Int? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("publicationDateUtc") val publicationDateUtc: String? = null,
    @SerializedName("price") val price: Double? = null,
    @SerializedName("country") val country: LocationResponse? = LocationResponse(),
    @SerializedName("city") val city: LocationResponse? = LocationResponse(),
    @SerializedName("currency") val currency: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("condition") val condition: ConditionType? = null,
    @SerializedName("game") val game: GameResponse? = GameResponse(),
    @SerializedName("author") val author: AuthorResponse? = AuthorResponse(),
)

fun MarketItemResponse?.toModel() = MarketGameItem(
    type = this?.type ?: MarketItemType.Sales,
    value = this?.value.orEmpty(),
    alias = this?.alias.orEmpty(),
    id = this?.id ?: 0,
    relationId = this?.relationId ?: 0,
    content = this?.content,
    publicationDateUtc = this?.publicationDateUtc?.toDate() ?: Date(),
    price = this?.price,
    country = this?.country.toModel(),
    city = this?.city.toModel(),
    currency = this?.currency,
    status = this?.status.orEmpty(),
    condition = this?.condition ?: ConditionType.Unknown,
    game = this?.game.toGameModel(),
    author = this?.author.toAuthorModel()
)