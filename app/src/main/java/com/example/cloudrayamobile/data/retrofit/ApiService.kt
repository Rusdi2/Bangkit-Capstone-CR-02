package com.example.cloudrayamobile.data.retrofit

import com.example.cloudrayamobile.data.retrofit.response.DashboardUserResponse
import com.example.cloudrayamobile.data.model.TokenRequestData
import com.example.cloudrayamobile.data.model.VmActionRequestData
import com.example.cloudrayamobile.data.retrofit.response.DetailUserResponse
import com.example.cloudrayamobile.data.retrofit.response.TokenRequestResponse
import com.example.cloudrayamobile.data.retrofit.response.VmDestroyResponse
import com.example.cloudrayamobile.data.retrofit.response.VmDetailResponse
import com.example.cloudrayamobile.data.retrofit.response.VmListDataResponse
import com.example.cloudrayamobile.data.retrofit.response.VmRebootResponse
import com.example.cloudrayamobile.data.retrofit.response.VmStartResponse
import com.example.cloudrayamobile.data.retrofit.response.VmStopResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("v1/api/gateway/user/auth")
    @Headers("Content-Type: application/json")
    suspend fun getTokenRequest(
        @Body request: TokenRequestData
    ): TokenRequestResponse

    @GET("v1/api/gateway/user/dashboard/list")
    suspend fun getDashBoardUser(): DashboardUserResponse

    @GET("v1/api/gateway/user/detail")
    suspend fun getDetailUser(): DetailUserResponse

    @GET("v1/api/gateway/user/virtualmachines")
    suspend fun getVmList(): VmListDataResponse

    @GET("v1/api/gateway/user/virtualmachines/{id}")
    suspend fun getVmDetail(
        @Path("id") id: String
    ): VmDetailResponse

    @POST("v1/api/gateway/user/virtualmachines/action")
    @Headers("Content-Type: application/json")
    suspend fun vmStart(
        @Body request: VmActionRequestData
    ): VmStartResponse

    @POST("v1/api/gateway/user/virtualmachines/action")
    @Headers("Content-Type: application/json")
    suspend fun vmStop(
        @Body request: VmActionRequestData
    ): VmStopResponse

    @POST("v1/api/gateway/user/virtualmachines/action")
    @Headers("Content-Type: application/json")
    suspend fun vmReboot(
        @Body request: VmActionRequestData
    ): VmRebootResponse

    @POST("v1/api/gateway/user/virtualmachines/action")
    @Headers("Content-Type: application/json")
    suspend fun vmDestroy(
        @Body request: VmActionRequestData
    ): VmDestroyResponse
}