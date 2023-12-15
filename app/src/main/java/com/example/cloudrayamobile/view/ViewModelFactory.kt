package com.example.cloudrayamobile.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cloudrayamobile.data.repository.CloudRayaRepository
import com.example.cloudrayamobile.injection.Injection
import com.example.cloudrayamobile.view.main.MainViewModel
import com.example.cloudrayamobile.view.registerList.RegisterListViewModel
import com.example.cloudrayamobile.view.siteList.SiteListViewModel
import com.example.cloudrayamobile.view.vmDetail.VmDetailViewModel
import com.example.cloudrayamobile.view.vmList.VmListViewModel

class ViewModelFactory private constructor(private val cloudRayaRepository: CloudRayaRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterListViewModel::class.java)) {
            return RegisterListViewModel(cloudRayaRepository) as T
        }else if (modelClass.isAssignableFrom(SiteListViewModel::class.java)) {
            return SiteListViewModel(cloudRayaRepository) as T
        }else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(cloudRayaRepository) as T
        }else if (modelClass.isAssignableFrom(VmListViewModel::class.java)) {
            return VmListViewModel(cloudRayaRepository) as T
        }else if (modelClass.isAssignableFrom(VmDetailViewModel::class.java)) {
            return VmDetailViewModel(cloudRayaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}