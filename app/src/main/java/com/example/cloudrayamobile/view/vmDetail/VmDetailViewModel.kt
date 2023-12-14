package com.example.cloudrayamobile.view.vmDetail

import androidx.lifecycle.ViewModel
import com.example.cloudrayamobile.data.model.VmActionRequestData
import com.example.cloudrayamobile.data.repository.CloudRayaRepository

class VmDetailViewModel (private val cloudRayaRepository: CloudRayaRepository) : ViewModel() {
    fun getVmDetail(id: String) = cloudRayaRepository.getVmDetail(id)

    fun vmStart(vmActionRequestData: VmActionRequestData) =
        cloudRayaRepository.vmStart(vmActionRequestData)

    fun vmStop(vmActionRequestData: VmActionRequestData) =
        cloudRayaRepository.vmStop(vmActionRequestData)

    fun vmReboot(vmActionRequestData: VmActionRequestData) =
        cloudRayaRepository.vmReboot(vmActionRequestData)

    fun vmDestroy(vmActionRequestData: VmActionRequestData) =
        cloudRayaRepository.vmDestroy(vmActionRequestData)
}