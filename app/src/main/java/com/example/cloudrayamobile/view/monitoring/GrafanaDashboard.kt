package com.example.cloudrayamobile.view.monitoring

import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.cloudrayamobile.R
import com.example.cloudrayamobile.databinding.ActivityGrafanaDashboardBinding
import com.google.android.material.appbar.MaterialToolbar


class GrafanaDashboard : AppCompatActivity() {
    private var _binding: ActivityGrafanaDashboardBinding? = null
    private val binding get() = _binding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityGrafanaDashboardBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.GrafanaWebView?.settings?.javaScriptEnabled = true
        binding?.GrafanaWebView?.settings?.domStorageEnabled = true
        binding?.GrafanaWebView?.loadUrl("http://202.43.249.22:3000/d/acb51992-a9e0-4609-87a9-df79f4809b4b/ubuntu-test-server")
        customizeAppBarTitle()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun customizeAppBarTitle() {
        val materialToolbar: MaterialToolbar = binding!!.siteListToAppBar
        val titleText = getString(R.string.vm_monitoring)
        val spannableString = SpannableString(titleText)
        val siteIndex = titleText.indexOf("VM")
        val listIndex = titleText.indexOf("Monitoring")

        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.navy)),
            siteIndex,
            siteIndex + 4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.baby_blue)),
            listIndex,
            listIndex + 10,
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