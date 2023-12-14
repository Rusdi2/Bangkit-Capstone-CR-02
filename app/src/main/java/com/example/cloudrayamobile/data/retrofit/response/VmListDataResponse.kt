package com.example.cloudrayamobile.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class VmListDataResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DataVmList? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class NetworkInfo(

	@field:SerializedName("vpc_id")
	val vpcId: Int? = null,

	@field:SerializedName("subnet_name")
	val subnetName: String? = null,

	@field:SerializedName("private_ip")
	val privateIp: String? = null
)

data class ServersItem(

	@field:SerializedName("snapshot_name")
	val snapshotName: String? = null,

	@field:SerializedName("snapshot_vm")
	val snapshotVm: String? = null,

	@field:SerializedName("public_ip")
	val publicIp: String? = null,

	@field:SerializedName("schedule_type")
	val scheduleType: String? = null,

	@field:SerializedName("local_id")
	val localId: Int? = null,

	@field:SerializedName("countrycode")
	val countrycode: String? = null,

	@field:SerializedName("template_label")
	val templateLabel: String? = null,

	@field:SerializedName("server_id")
	val serverId: String? = null,

	@field:SerializedName("project_tag_name")
	val projectTagName: Any? = null,

	@field:SerializedName("location_id")
	val locationId: Int? = null,

	@field:SerializedName("network_info")
	val networkInfo: NetworkInfo? = null,

	@field:SerializedName("project_tag")
	val projectTag: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("template_type")
	val templateType: String? = null,

	@field:SerializedName("project_tag_id")
	val projectTagId: Any? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("launch_date")
	val launchDate: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataVmList(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("recordsFiltered")
	val recordsFiltered: Int? = null,

	@field:SerializedName("servers")
	val servers: List<ServersItem?>? = null,

	@field:SerializedName("total_page")
	val totalPage: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("recordsTotal")
	val recordsTotal: Int? = null
)
