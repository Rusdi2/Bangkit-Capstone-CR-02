package com.example.cloudrayamobile.view.registerList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudrayamobile.data.local.roomDb.SiteListEntity
import com.example.cloudrayamobile.data.repository.CloudRayaRepository
import kotlinx.coroutines.launch


class RegisterListViewModel(private val cloudRayaRepository: CloudRayaRepository) : ViewModel() {
    fun insertSite(siteListEntity: SiteListEntity){
        viewModelScope.launch {
            cloudRayaRepository.insertSite(siteListEntity)
        }
    }
}