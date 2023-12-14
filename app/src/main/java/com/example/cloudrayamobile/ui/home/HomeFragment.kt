package com.example.cloudrayamobile.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cloudrayamobile.R
import com.example.cloudrayamobile.data.retrofit.result.ResultState
import com.example.cloudrayamobile.databinding.FragmentHomeBinding
import com.example.cloudrayamobile.utils.showLoading
import com.example.cloudrayamobile.utils.showToast
import com.example.cloudrayamobile.view.ViewModelFactory
import com.example.cloudrayamobile.view.main.MainViewModel
import com.example.cloudrayamobile.view.vmList.VmList

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.cardView?.setOnClickListener {
            startActivity(Intent(requireActivity(), VmList::class.java))
        }

        _binding?.tvSeeVmList?.setOnClickListener {
            startActivity(Intent(requireActivity(), VmList::class.java))
        }

        getDashboardUserData()
    }

    private fun getDashboardUserData(){
        viewModel.getDashboardUser().observe(requireActivity()){ result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(_binding!!.progressBar, true)
                }
                is ResultState.Success -> {
                    val dashboardUserDataList = result.data
                    if (dashboardUserDataList.isNotEmpty()) {
                        val dashboardData = dashboardUserDataList[0]
                        _binding?.apply {
                            svHome.visibility = View.VISIBLE
                            tvNumberTotalVm.text = dashboardData.totalVm.toString()
                            tvNumberRunningVm.text = dashboardData.totalOnline.toString()
                            tvNumberStoppedVm.text = dashboardData.totalStopped.toString()
                            tvNumberTotalDisk.text = dashboardData.totalDisk.toString()
                            tvNumberTotalCpu.text = dashboardData.totalCpu.toString()
                            tvNumberTotalRam.text = dashboardData.totalRam.toString()
                        }
                    } else {
                        showToast(requireContext(), getString(R.string.empty_data))
                    }
                    showLoading(_binding!!.progressBar, false)
                }
                is ResultState.Error -> {
                    showToast(requireContext(), result.error)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}