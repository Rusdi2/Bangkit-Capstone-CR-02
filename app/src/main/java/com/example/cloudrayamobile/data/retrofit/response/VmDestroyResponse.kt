package com.example.cloudrayamobile.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class VmDestroyResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
