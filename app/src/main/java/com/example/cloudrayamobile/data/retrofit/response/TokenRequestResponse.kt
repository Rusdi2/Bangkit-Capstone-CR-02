package com.example.cloudrayamobile.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class TokenRequestResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("refresh_token")
	val refreshToken: String? = null,

	@field:SerializedName("bearer_token")
	val bearerToken: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("expired_at")
	val expiredAt: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
