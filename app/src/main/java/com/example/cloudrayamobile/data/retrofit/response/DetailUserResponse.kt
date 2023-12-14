package com.example.cloudrayamobile.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val dataDetailUser: DataDetailUser? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataDetailUser(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("firstname")
	val firstname: String? = null,

	@field:SerializedName("status_words")
	val statusWords: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("address1")
	val address1: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("lastname")
	val lastname: String? = null,

	@field:SerializedName("enable_vpc")
	val enableVpc: Int? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("ballance")
	val ballance: Ballance? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("mobile_phone")
	val mobilePhone: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("currency")
	val currency: Currency? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("billingtype")
	val billingtype: String? = null,

	@field:SerializedName("postal_code")
	val postalCode: String? = null,

	@field:SerializedName("is_use_secprof")
	val isUseSecprof: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("phone_code")
	val phoneCode: String? = null
)

data class Ballance(

	@field:SerializedName("current_cost")
	val currentCost: Any? = null,

	@field:SerializedName("current_balance")
	val currentBalance: Any? = null
)

data class Currency(

	@field:SerializedName("symbol")
	val symbol: String? = null,

	@field:SerializedName("initial")
	val initial: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
