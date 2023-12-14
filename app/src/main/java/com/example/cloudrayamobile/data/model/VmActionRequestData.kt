package com.example.cloudrayamobile.data.model

import com.google.gson.annotations.SerializedName

data class VmActionRequestData(
    @SerializedName("vm_id") val vmId: String,
    @SerializedName("request") val request: String,
    @SerializedName("release_ip") val releaseIp: Boolean
)