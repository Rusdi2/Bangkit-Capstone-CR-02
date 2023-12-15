package com.example.cloudrayamobile.view.vmDetail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.cloudrayamobile.R
import com.example.cloudrayamobile.data.model.VmActionRequestData
import com.example.cloudrayamobile.data.retrofit.result.ResultState
import com.example.cloudrayamobile.databinding.ActivityVmDetailBinding
import com.example.cloudrayamobile.utils.loadImageBasedOnCountryCode
import com.example.cloudrayamobile.utils.loadImageBasedOnOperatingSystem
import com.example.cloudrayamobile.utils.loadImageBasedOnState
import com.example.cloudrayamobile.utils.showLoading
import com.example.cloudrayamobile.utils.showToast
import com.example.cloudrayamobile.view.ViewModelFactory
import com.example.cloudrayamobile.view.monitoring.GrafanaDashboard
import com.example.cloudrayamobile.view.vmList.VmList
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.Date

class VmDetail : AppCompatActivity() {
    private var _binding: ActivityVmDetailBinding? = null
    private val binding get() = _binding
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var clipData: ClipData
    private var currentVmState: String = ""
    private var request: String = ""
    private var releaseIp: Boolean = false
    private val viewModel by viewModels<VmDetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityVmDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val localId = intent.getStringExtra(EXTRA_ID)

        if (localId != null) {
            viewModel.getVmDetail(localId).observe(this){result ->
                when(result) {
                    is ResultState.Loading -> {
                        showLoading(binding!!.progressBar, true)
                    }
                    is ResultState.Success -> {
                        val vmData = result.data
                        if (vmData.isNotEmpty()){
                            val data = vmData[0]
                            binding?.apply {
                                currentVmState = data.state.toString()

                                showLoading(binding!!.progressBar, false)

                                svVmDetail.visibility = View.VISIBLE
                                btnToMonitoringDahsboard.visibility = View.VISIBLE

                                linearLayoutBackgroundState(data.state.toString(), lyVmDetailStatus)
                                loadImageBasedOnCountryCode(data.country.toString(), vmDetailCountryFlag)
                                loadImageBasedOnState(data.state.toString(), ivVmDetailStatus)
                                loadImageBasedOnOperatingSystem(data.os.toString(), ivVmDetailOs)
                                textCopy(data.publicIp.toString())

                                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                val currentDate = sdf.format(Date())

                                tvDetailHostName.text = data.hostname
                                val privateIpString = data.privateIp?.joinToString("\n") ?: 0
                                tvDetailProjectTag.text = setProjectTagText(data.projectTag.toString())
                                tvDetailLaunchedDate.text = data.launchDate
                                tvVmDetailLocation.text = data.location
                                tvVmDetailState.text = currentVmState
                                updateTextStateColor(data.state.toString(), tvVmDetailState)
                                tvVmUsername.text = data.username
                                tvVmCreatedDate.text = data.createdDate
                                tvVmPeriodLaunchDate.text = data.launchDate
                                tvVmPeriodDate.text = setTextView(R.string.vm_period_date_content, currentDate)
                                tvVmDetailTypePackage.text = data.jsonMemberPackage
                                tvCpuAmount.text = setTextView(
                                    R.string.cpu_amount,
                                    data.cpu.toString(),
                                )
                                tvMemoryAmount.text = setTextView(
                                    R.string.memory_amount,
                                    data.memory.toString(),
                                )
                                tvStorageAmount.text = setTextView(
                                    R.string.storage_amount,
                                    data.rootdiskSize.toString(),
                                )
                                tvOsName.text = data.os
                                tvPublicIp.text = data.publicIp
                                tvPrivateIp.text = privateIpString.toString()
                                tvVpcNetworkContent.text = setTextView(
                                    R.string.vpc_network_content,
                                    data.vpc.toString(),
                                )
                                tvDetailSubnet.text = setTextView(
                                    R.string.detail_subnet,
                                    data.vpcNetwork.toString(),
                                )
                                tvSshKey.text = data.sshkeypair?.name

                                updatePlayPauseBtn(currentVmState)
                                updateRestartBgBtn(currentVmState)
                                btnStart.setOnClickListener {
                                    playPauseBtnAction(localId)
                                }
                                btnToMonitoringDahsboard.setOnClickListener {
                                    startActivity(Intent(this@VmDetail, GrafanaDashboard::class.java))
                                }
                                restartBtnActivation(localId)
                                deleteBtnListener(localId)
                            }
                        }
                    }
                    is ResultState.Error -> {}
                }
            }
        }

        customizeAppBarTitle()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setTextView(stringResources: Int, content: String): CharSequence? {
        return resources.getString(stringResources, content)
    }

    private fun setProjectTagText(projectTag: String): CharSequence? {
        return when (projectTag) {
            "null" -> {
                getString(R.string.no_project_tag)
            }
            else -> {
                projectTag
            }
        }
    }

    private fun linearLayoutBackgroundState(state: String, linearLayout: LinearLayout) {
        runOnUiThread {
            val colorResId = when (state) {
                getString(R.string.running) -> R.color.success_light
                getString(R.string.stopped) -> R.color.danger_light
                else -> R.color.white
            }

            setLinearLayoutBackgroundColor(colorResId, linearLayout)
        }
    }

    private fun setLinearLayoutBackgroundColor(color: Int, linearLayout: LinearLayout) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(linearLayout.context, color))
    }

    private fun updateTextStateColor(state: String, textView: TextView) {
        runOnUiThread {
            val drawableResId = when (state) {
                getString(R.string.running) -> R.color.success_active
                getString(R.string.stopped)-> R.color.danger_default
                else -> R.color.white
            }

            setTextStateColor(drawableResId, textView)
        }
    }

    private fun setTextStateColor(colorResId: Int, textView: TextView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(getColor(colorResId))
        } else {
            @Suppress("DEPRECATION")
            textView.setTextColor(resources.getColor(colorResId))
        }
    }

    private fun textCopy(textCopy : String){
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        binding?.btnCopy?.setOnClickListener {
            clipData = ClipData.newPlainText(getString(R.string.copied_text), textCopy)
            clipboardManager.setPrimaryClip(clipData)
            showToast(this, getString(R.string.copied_text))
        }
    }

    private fun updatePlayPauseBtn(vmState: String){
        runOnUiThread {
            binding?.apply {
                if (vmState == getString(R.string.running)) {
                    btnStart.setBackgroundResource(R.drawable.btn_pause_bg)
                } else {
                    btnStart.setBackgroundResource(R.drawable.btn_play_bg)
                }
                btnRestart.isEnabled = (vmState == "Running")
            }
        }
    }

    private fun updateRestartBgBtn(vmState: String){
        runOnUiThread {
            binding?.apply {
                if (vmState == getString(R.string.running)) {
                    btnRestart.setBackgroundResource(R.drawable.btn_restart_bg)
                } else {
                    btnRestart.setBackgroundResource(R.drawable.btn_restart_disable)
                }
            }
        }
    }

    private fun playPauseBtnAction(vmId: String){
        if (currentVmState == getString(R.string.running)){
            request = "stop"
             val vmActionRequestData = VmActionRequestData(
                 vmId,
                 request,
                 releaseIp
             )
            viewModel.vmStop(vmActionRequestData).observe(this) { result ->
                if(result != null){
                    when(result){
                        is ResultState.Loading -> {
                            showLoading(binding!!.progressBar, true)
                        }
                        is ResultState.Success -> {
                            showLoading(binding!!.progressBar, false)

                            currentVmState = getString(R.string.stopped)
                            showToast(this, getString(R.string.vm_stopped))
                            updatePlayPauseBtn(currentVmState)
                            updateRestartBgBtn(currentVmState)
                            updateTextStateColor(currentVmState, binding!!.tvVmDetailState)
                            linearLayoutBackgroundState(currentVmState, binding!!.lyVmDetailStatus)
                            loadImageBasedOnState(currentVmState, binding!!.ivVmDetailStatus)
                            binding?.tvVmDetailState?.text = currentVmState
                        }
                        is ResultState.Error -> {
                            showToast(this, result.error)
                        }
                    }
                }
            }
        } else {
            request = "start"
            val vmActionRequestData = VmActionRequestData(
                vmId,
                request,
                releaseIp
            )
            viewModel.vmStart(vmActionRequestData).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(binding!!.progressBar, true)
                        }
                        is ResultState.Success -> {
                            showLoading(binding!!.progressBar, false)

                            currentVmState = getString(R.string.running)
                            showToast(this, getString(R.string.vm_started))
                            updatePlayPauseBtn(currentVmState)
                            updateRestartBgBtn(currentVmState)
                            updateTextStateColor(currentVmState, binding!!.tvVmDetailState)
                            linearLayoutBackgroundState(currentVmState, binding!!.lyVmDetailStatus)
                            loadImageBasedOnState(currentVmState, binding!!.ivVmDetailStatus)
                            binding?.tvVmDetailState?.text = currentVmState
                        }
                        is ResultState.Error -> {
                            showToast(this, result.error)
                        }
                    }
                }
            }
        }
    }

    private fun restartBtnActivation(vmId: String) {
        binding?.btnRestart?.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Restart VM")
                setMessage("Restart Virtual Machines?")
                setPositiveButton("Yes") { _, _ ->
                    handleRestartAction(vmId)
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            }.create().show()
        }
    }

    private fun handleRestartAction(vmId: String) {
            request = "reboot"
            val vmActionRequestData = VmActionRequestData(
                vmId,
                request,
                releaseIp
            )
            viewModel.vmReboot(vmActionRequestData).observe(this@VmDetail) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(binding!!.progressBar, true)
                        }
                        is ResultState.Success -> {
                            showLoading(binding!!.progressBar, false)

                            currentVmState = getString(R.string.running)
                            showToast(this, getString(R.string.vm_restarted))
                            updatePlayPauseBtn(currentVmState)
                            updateRestartBgBtn(currentVmState)
                            updateTextStateColor(currentVmState, binding!!.tvVmDetailState)
                            linearLayoutBackgroundState(currentVmState, binding!!.lyVmDetailStatus)
                            loadImageBasedOnState(currentVmState, binding!!.ivVmDetailStatus)
                            binding?.tvVmDetailState?.text = currentVmState
                        }
                        is ResultState.Error -> {
                            showToast(this@VmDetail, result.error)
                        }
                    }
                }
            }
        }

    private fun deleteBtnListener(vmId: String) {
        binding?.btnDelete?.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Restart VM")
                setMessage("Delete Virtual Machines?")
                setPositiveButton("Yes") { _, _ ->
                    handleDeleteAction(vmId)
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            }.create().show()
        }
    }

    private fun handleDeleteAction(vmId: String) {
        request = "destroy"
        releaseIp = true
        val vmActionRequestData = VmActionRequestData(
            vmId,
            request,
            releaseIp
        )
        viewModel.vmDestroy(vmActionRequestData).observe(this@VmDetail) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(binding!!.progressBar, true)
                    }
                    is ResultState.Success -> {
                        showLoading(binding!!.progressBar, false)
                        startActivity(Intent(this, VmList::class.java))
                    }
                    is ResultState.Error -> {
                        showToast(this@VmDetail, result.error)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun customizeAppBarTitle() {
        val materialToolbar: MaterialToolbar = binding!!.siteListToAppBar
        val titleText = getString(R.string.vm_detail)
        val spannableString = SpannableString(titleText)
        val siteIndex = titleText.indexOf("VM")
        val listIndex = titleText.indexOf("Detail")

        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.navy)),
            siteIndex,
            siteIndex + 4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.baby_blue)),
            listIndex,
            listIndex + 6,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        materialToolbar.title = spannableString
        setSupportActionBar(materialToolbar)
    }

    companion object{
        const val EXTRA_ID = "extra_id"
    }
}