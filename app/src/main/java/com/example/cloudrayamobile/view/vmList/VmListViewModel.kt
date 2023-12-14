package com.example.cloudrayamobile.view.vmList

import androidx.lifecycle.ViewModel
import com.example.cloudrayamobile.data.repository.CloudRayaRepository

class VmListViewModel (private val cloudRayaRepository: CloudRayaRepository) : ViewModel() {
    fun getVmList() = cloudRayaRepository.getVmList()
}