package com.example.cloudrayamobile.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.cloudrayamobile.data.local.roomDb.CloudRayaDao
import com.example.cloudrayamobile.data.local.roomDb.SiteListEntity
import com.example.cloudrayamobile.data.local.sharedPref.TokenAuth
import com.example.cloudrayamobile.data.local.sharedPref.TokenPreference
import com.example.cloudrayamobile.data.retrofit.ApiConfig
import com.example.cloudrayamobile.data.retrofit.ApiService
import com.example.cloudrayamobile.data.retrofit.response.DashboardData
import com.example.cloudrayamobile.data.retrofit.response.DashboardUserResponse
import com.example.cloudrayamobile.data.retrofit.response.ServersItem
import com.example.cloudrayamobile.data.model.TokenRequestData
import com.example.cloudrayamobile.data.model.VmActionRequestData
import com.example.cloudrayamobile.data.retrofit.response.DataDetailUser
import com.example.cloudrayamobile.data.retrofit.response.DetailUserResponse
import com.example.cloudrayamobile.data.retrofit.response.TokenRequestResponse
import com.example.cloudrayamobile.data.retrofit.response.VmDestroyResponse
import com.example.cloudrayamobile.data.retrofit.response.VmDetailData
import com.example.cloudrayamobile.data.retrofit.response.VmListDataResponse
import com.example.cloudrayamobile.data.retrofit.response.VmRebootResponse
import com.example.cloudrayamobile.data.retrofit.response.VmStartResponse
import com.example.cloudrayamobile.data.retrofit.response.VmStopResponse
import com.example.cloudrayamobile.data.retrofit.result.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CloudRayaRepository private constructor(
    private val cloudRayaDao: CloudRayaDao,
    private val apiService: ApiService,
    private val tokenPreference: TokenPreference
){
    suspend fun insertSite(siteListEntity: SiteListEntity){
        withContext(Dispatchers.IO) {
            cloudRayaDao.insertSite(siteListEntity)
        }
    }

    fun getAllSiteList(): LiveData<List<SiteListEntity>>{
        return cloudRayaDao.getAllSiteList()
    }

    suspend fun deleteSite(siteListEntity: SiteListEntity){
        withContext(Dispatchers.IO) {
            cloudRayaDao.deleteSite(siteListEntity)
        }
    }

    suspend fun saveSiteAuth(tokenAuth: TokenAuth){
        tokenPreference.saveTokenAuth(tokenAuth)
    }

    fun siteLogin(tokenRequestData: TokenRequestData) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getTokenRequest(tokenRequestData)
            val tokenAuth = TokenAuth(
                bearerToken = successResponse.data?.bearerToken ?: "",
                username = successResponse.data?.username ?: "",
            )
            tokenPreference.saveTokenAuth(tokenAuth)
            emit(ResultState.Success(successResponse))
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, TokenRequestResponse::class.java)
            emit(errorResponse.message?.let { ResultState.Error(it) })
        }
    }

    fun getDashboardUser(): LiveData<ResultState<List<DashboardData>>> = liveData {
        emit(ResultState.Loading)
        try {
            val token = runBlocking { tokenPreference.getTokenAuth().first() }
            val apiService = ApiConfig.getApiService(token.bearerToken)
            val successResponse = apiService.getDashBoardUser()
            val dashboardUserData = successResponse.dashboardData?.let { listOf(it) } ?: emptyList()
            emit(ResultState.Success(dashboardUserData))
        }catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, DashboardUserResponse::class.java)
            emit(ResultState.Error("$errorResponse"))
        }
    }

    fun getDetailUser(): LiveData<ResultState<List<DataDetailUser>>> = liveData {
        emit(ResultState.Loading)
        try {
            val token = runBlocking { tokenPreference.getTokenAuth().first() }
            val apiService = ApiConfig.getApiService(token.bearerToken)
            val successResponse = apiService.getDetailUser()
            val detailUserData = successResponse.dataDetailUser?.let { listOf(it) } ?: emptyList()
            emit(ResultState.Success(detailUserData))
        }catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, DetailUserResponse::class.java)
            emit(ResultState.Error("$errorResponse"))
        }
    }

    fun getVmList(): LiveData<ResultState<List<ServersItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val token = runBlocking { tokenPreference.getTokenAuth().first() }
            val apiService = ApiConfig.getApiService(token.bearerToken)
            val successResponse = apiService.getVmList()
            val servers = successResponse.data?.servers
            val serversList = servers!!.map {
                ServersItem(
                    it?.snapshotName,
                    it?.snapshotVm,
                    it?.publicIp,
                    it?.scheduleType,
                    it?.localId,
                    it?.countrycode,
                    it?.templateLabel,
                    it?.serverId,
                    it?.projectTagName,
                    it?.locationId,
                    it?.networkInfo,
                    it?.projectTag,
                    it?.name,
                    it?.location,
                    it?.templateType,
                    it?.projectTagId,
                    it?.state,
                    it?.launchDate,
                    it?.status
                )
            }
            emit(ResultState.Success(serversList))
        }catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, VmListDataResponse::class.java)
            emit(ResultState.Error("$errorResponse"))
        }
    }

    fun getVmDetail(id: String): LiveData<ResultState<List<VmDetailData>>> = liveData{
        emit(ResultState.Loading)
        try {
            val token = runBlocking { tokenPreference.getTokenAuth().first() }
            val apiService = ApiConfig.getApiService(token.bearerToken)
            val successResponse = apiService.getVmDetail(id)
            val vmDetailData = successResponse.vmDetailData?.let { listOf(it) } ?: emptyList()
            emit(ResultState.Success(vmDetailData))
        }catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, VmListDataResponse::class.java)
            emit(ResultState.Error("$errorResponse"))
        }
    }

    fun vmStart(vmActionRequestData: VmActionRequestData) = liveData {
        emit(ResultState.Loading)
        try {
            val token = runBlocking { tokenPreference.getTokenAuth().first() }
            val apiService = ApiConfig.getApiService(token.bearerToken)
            val successResponse = apiService.vmStart(vmActionRequestData)
            emit(ResultState.Success(successResponse))
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, VmStartResponse::class.java)
            emit(errorResponse.message?.let { ResultState.Error(it) })
        }
    }

    fun vmStop(vmActionRequestData: VmActionRequestData) = liveData {
        emit(ResultState.Loading)
        try {
            val token = runBlocking { tokenPreference.getTokenAuth().first() }
            val apiService = ApiConfig.getApiService(token.bearerToken)
            val successResponse = apiService.vmStop(vmActionRequestData)
            emit(ResultState.Success(successResponse))
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, VmStopResponse::class.java)
            emit(errorResponse.message?.let { ResultState.Error(it) })
        }
    }

    fun vmReboot(vmActionRequestData: VmActionRequestData) = liveData {
        emit(ResultState.Loading)
        try {
            val token = runBlocking { tokenPreference.getTokenAuth().first() }
            val apiService = ApiConfig.getApiService(token.bearerToken)
            val successResponse = apiService.vmReboot(vmActionRequestData)
            emit(ResultState.Success(successResponse))
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, VmRebootResponse::class.java)
            emit(errorResponse.message?.let { ResultState.Error(it) })
        }
    }

    fun vmDestroy(vmActionRequestData: VmActionRequestData) = liveData {
        emit(ResultState.Loading)
        try {
            val token = runBlocking { tokenPreference.getTokenAuth().first() }
            val apiService = ApiConfig.getApiService(token.bearerToken)
            val successResponse = apiService.vmDestroy(vmActionRequestData)
            emit(ResultState.Success(successResponse))
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, VmDestroyResponse::class.java)
            emit(errorResponse.message?.let { ResultState.Error(it) })
        }
    }

    companion object {
        @Volatile
        private var instance: CloudRayaRepository? = null
        fun getInstance(cloudRayaDao: CloudRayaDao, apiService: ApiService, pref: TokenPreference) =
            instance ?: synchronized(this) {
                instance ?: CloudRayaRepository(cloudRayaDao, apiService, pref)
            }.also { instance = it }
    }
}