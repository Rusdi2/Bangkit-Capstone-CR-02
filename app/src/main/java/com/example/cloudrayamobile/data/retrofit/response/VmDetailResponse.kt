package com.example.cloudrayamobile.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class VmDetailResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val vmDetailData: VmDetailData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Sshkeypair(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("cs_name")
	val csName: String? = null
)

data class VmDetailData(

	@field:SerializedName("maydestroy")
	val maydestroy: Int? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("suspend_date")
	val suspendDate: String? = null,

	@field:SerializedName("public_ip")
	val publicIp: String? = null,

	@field:SerializedName("memory")
	val memory: Int? = null,

	@field:SerializedName("vpc_id")
	val vpcId: Int? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("package_id")
	val packageId: Int? = null,

	@field:SerializedName("private_ip")
	val privateIp: List<String?>? = null,

	@field:SerializedName("location_id")
	val locationId: Int? = null,

	@field:SerializedName("security_profile")
	val securityProfile: Any? = null,

	@field:SerializedName("hostname")
	val hostname: String? = null,

	@field:SerializedName("sshkeypair")
	val sshkeypair: Sshkeypair? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("rootdisk_size")
	val rootdiskSize: Int? = null,

	@field:SerializedName("security_id")
	val securityId: Int? = null,

	@field:SerializedName("disk_id")
	val diskId: String? = null,

	@field:SerializedName("package")
	val jsonMemberPackage: String? = null,

	@field:SerializedName("os")
	val os: String? = null,

	@field:SerializedName("vpc")
	val vpc: String? = null,

	@field:SerializedName("cpu")
	val cpu: String? = null,

	@field:SerializedName("ostype")
	val ostype: String? = null,

	@field:SerializedName("vpc_network")
	val vpcNetwork: String? = null,

	@field:SerializedName("move_subnet_progress")
	val moveSubnetProgress: Int? = null,

	@field:SerializedName("disk")
	val disk: String? = null,

	@field:SerializedName("project_tag")
	val projectTag: Any? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("launch_date")
	val launchDate: String? = null,

	@field:SerializedName("created_date")
	val createdDate: String? = null,

	@field:SerializedName("image_id")
	val imageId: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
