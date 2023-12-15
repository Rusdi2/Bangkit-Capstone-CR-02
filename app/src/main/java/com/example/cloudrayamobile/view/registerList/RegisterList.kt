package com.example.cloudrayamobile.view.registerList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.cloudrayamobile.R
import com.example.cloudrayamobile.data.local.roomDb.SiteListEntity
import com.example.cloudrayamobile.databinding.ActivityRegisterListBinding
import com.example.cloudrayamobile.utils.showToast
import com.example.cloudrayamobile.view.ViewModelFactory
import com.example.cloudrayamobile.view.siteList.SiteList

class RegisterList : AppCompatActivity() {
    private var _binding: ActivityRegisterListBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<RegisterListViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.tvSiteList?.setOnClickListener {
            startActivity(Intent(this, SiteList::class.java))
        }

        registerSite()
    }

    private fun registerSite(){
        binding?.btnRegisterList?.setOnClickListener {
            val siteName = binding?.siteNameEditText?.text.toString().trim()
            val appKey = binding?.appKeyEditText?.text.toString().trim()
            val secretKey = binding?.secretKeyEditText?.text.toString().trim()

            when{
                siteName.isEmpty() -> {
                    binding?.siteNameEditText?.error = getString(R.string.empty_field)
                }
                appKey.isEmpty() -> {
                    binding?.appKeyEditText?.error = getString(R.string.empty_field)
                }
                secretKey.isEmpty() -> {
                    binding?.secretKeyEditText?.error = getString(R.string.empty_field)
                } else -> {
                    val siteListEntity = SiteListEntity(
                        siteName = siteName,
                        appKey = appKey,
                        secretKey = secretKey
                    )
                    viewModel.insertSite(siteListEntity)
                    showToast(this, getString(R.string.success_regist_site))
                    startActivity(Intent(this@RegisterList, SiteList::class.java))
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}