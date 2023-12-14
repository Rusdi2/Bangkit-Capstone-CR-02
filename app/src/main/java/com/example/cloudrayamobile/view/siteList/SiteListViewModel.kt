package com.example.cloudrayamobile.view.siteList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudrayamobile.R
import com.example.cloudrayamobile.data.local.roomDb.SiteListEntity
import com.example.cloudrayamobile.data.local.sharedPref.TokenAuth
import com.example.cloudrayamobile.data.repository.CloudRayaRepository
import com.example.cloudrayamobile.data.model.TokenRequestData
import com.example.cloudrayamobile.utils.Event
import kotlinx.coroutines.launch

class SiteListViewModel(private val cloudRayaRepository: CloudRayaRepository) : ViewModel() {
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _undo = MutableLiveData<Event<SiteListEntity>>()
    val undo: LiveData<Event<SiteListEntity>> = _undo

    fun getAllSiteList() = cloudRayaRepository.getAllSiteList()

    fun deleteSite(siteListEntity: SiteListEntity){
        viewModelScope.launch {
            cloudRayaRepository.deleteSite(siteListEntity)
            _snackbarText.value = Event(R.string.site_deleted)
            _undo.value = Event(siteListEntity)
        }
    }

    fun insertSite(siteListEntity: SiteListEntity){
        viewModelScope.launch {
            cloudRayaRepository.insertSite(siteListEntity)
        }
    }

    fun siteLogin(tokenRequestData: TokenRequestData) =
        cloudRayaRepository.siteLogin(tokenRequestData)

    fun saveSiteAuth(tokenAuth: TokenAuth){
        viewModelScope.launch {
            cloudRayaRepository.saveSiteAuth(tokenAuth)
        }
    }
}