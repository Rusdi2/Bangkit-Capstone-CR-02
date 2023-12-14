package com.example.cloudrayamobile.data.local.sharedPref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "site_session")

class TokenPreference private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun saveTokenAuth(tokenAuth: TokenAuth){
        dataStore.edit { preferences ->
            preferences[BEARER_TOKEN] = tokenAuth.bearerToken
            preferences[USERNAME] = tokenAuth.username
        }
    }

    fun getTokenAuth(): Flow<TokenAuth> {
        return dataStore.data.map {preferences ->
            TokenAuth(
                preferences[BEARER_TOKEN] ?: "",
                preferences[USERNAME] ?: "",
            )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TokenPreference? = null
        private val BEARER_TOKEN = stringPreferencesKey("bearerToken")
        private val USERNAME = stringPreferencesKey("username")

        fun getInstance(dataStore: DataStore<Preferences>): TokenPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = TokenPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}