package com.example.cloudrayamobile.injection

import android.content.Context
import com.example.cloudrayamobile.data.local.roomDb.CloudRayaDatabase
import com.example.cloudrayamobile.data.local.sharedPref.TokenPreference
import com.example.cloudrayamobile.data.local.sharedPref.dataStore
import com.example.cloudrayamobile.data.repository.CloudRayaRepository
import com.example.cloudrayamobile.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): CloudRayaRepository{
        val database = CloudRayaDatabase.getInstance(context)
        val dao = database.siteListDao()
        val pref = TokenPreference.getInstance(context.dataStore)
        val token = runBlocking { pref.getTokenAuth().first() }
        val apiService = ApiConfig.getApiService(token.bearerToken)
        return CloudRayaRepository.getInstance(dao, apiService, pref)
    }
}