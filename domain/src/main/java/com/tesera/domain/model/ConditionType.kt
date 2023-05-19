package com.tesera.domain.model

import com.google.gson.annotations.SerializedName

enum class ConditionType {
    @SerializedName("New")
    New,

    @SerializedName("Used")
    Used,

    @SerializedName("Any")
    Any,

    @SerializedName("Unknown")
    Unknown
}