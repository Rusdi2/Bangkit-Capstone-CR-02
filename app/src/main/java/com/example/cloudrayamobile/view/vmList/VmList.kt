package com.example.cloudrayamobile.view.vmList

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloudrayamobile.R
import com.example.cloudrayamobile.data.retrofit.response.ServersItem
import com.example.cloudrayamobile.data.retrofit.result.ResultState
import com.example.cloudrayamobile.databinding.ActivityVmListBinding
import com.example.cloudrayamobile.utils.showLoading
import com.example.cloudrayamobile.utils.showToast
import com.example.cloudrayamobile.view.ViewModelFactory
import com.example.cloudrayamobile.view.adapter.VmListAdapter
import com.example.cloudrayamobile.view.vmDetail.VmDetail
import com.google.android.material.appbar.MaterialToolbar

class VmList : AppCompatActivity() {
    private var _binding: ActivityVmListBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<VmListViewModel> {
        ViewModelFactory.getInstance(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityVmListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val layoutManager = LinearLayoutManager(this)
        binding?.rvVmList?.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvVmList?.addItemDecoration(itemDecoration)

        viewModel.getVmList().observe(this){result ->
            when(result){
                is ResultState.Loading -> {
                    showLoading(binding!!.progressBar, true)
                }
                is ResultState.Success -> {
                    showLoading(binding!!.progressBar, false)
                    setVmList(result.data)
                }
                is ResultState.Error -> {
                    showToast(this, result.error)
                }
            }
        }

        customizeAppBarTitle()
    }

    private fun setVmList(serversItem: List<ServersItem>){
        val adapter = VmListAdapter()
        adapter.submitList(serversItem)
        binding?.rvVmList?.adapter = adapter

        adapter.setOnItemClickCallback(object : VmListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ServersItem) {
                Intent(this@VmList, VmDetail::class.java).also {
                    it.putExtra(VmDetail.EXTRA_ID, data.localId.toString())
                    startActivity(it)
                }
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun customizeAppBarTitle() {
        val materialToolbar: MaterialToolbar = binding!!.siteListToAppBar
        val titleText = getString(R.string.vm_list)
        val spannableString = SpannableString(titleText)

        val siteIndex = titleText.indexOf("VM")
        val listIndex = titleText.indexOf("List")

        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.navy)),
            siteIndex,
            siteIndex + 4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.baby_blue)),
            listIndex,
            listIndex + 4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        materialToolbar.title = spannableString
        setSupportActionBar(materialToolbar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}