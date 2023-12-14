package com.example.cloudrayamobile.view.main

import androidx.lifecycle.ViewModel
import com.example.cloudrayamobile.data.repository.CloudRayaRepository


class MainViewModel (private val cloudRayaRepository: CloudRayaRepository) : ViewModel() {
    fun getDashboardUser() = cloudRayaRepository.getDashboardUser()

    fun getDetailUser() = cloudRayaRepository.getDetailUser()
}