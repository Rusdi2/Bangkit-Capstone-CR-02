package com.example.cloudrayamobile.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class DashboardUserResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val dashboardData: DashboardData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DashboardData(

	@field:SerializedName("total_bucket")
	val totalBucket: Int? = null,

	@field:SerializedName("total_suspend")
	val totalSuspend: Int? = null,

	@field:SerializedName("total_vm")
	val totalVm: Int? = null,

	@field:SerializedName("total_num_datadisk")
	val totalNumDatadisk: Int? = null,

	@field:SerializedName("total_stopped")
	val totalStopped: Int? = null,

	@field:SerializedName("total_num_disk")
	val totalNumDisk: Int? = null,

	@field:SerializedName("total_cpu")
	val totalCpu: Int? = null,

	@field:SerializedName("total_disk")
	val totalDisk: Int? = null,

	@field:SerializedName("total_ip")
	val totalIp: Int? = null,

	@field:SerializedName("total_unpaid")
	val totalUnpaid: Int? = null,

	@field:SerializedName("total_overdue")
	val totalOverdue: Int? = null,

	@field:SerializedName("total_online")
	val totalOnline: Int? = null,

	@field:SerializedName("total_active")
	val totalActive: Int? = null,

	@field:SerializedName("total_ram")
	val totalRam: Int? = null
)
