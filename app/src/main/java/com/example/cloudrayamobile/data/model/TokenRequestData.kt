package com.example.cloudrayamobile.data.model

import com.google.gson.annotations.SerializedName

data class TokenRequestData(
    @SerializedName("app_key") val appKey: String,
    @SerializedName("secret_key") val secretKey: String
)