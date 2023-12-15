package com.example.cloudrayamobile.ui.notifications

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import com.example.cloudrayamobile.R
import com.example.cloudrayamobile.databinding.ActivityNotificationDetailBinding
import com.google.android.material.appbar.MaterialToolbar

class NotificationDetailActivity : AppCompatActivity() {
    private var _binding: ActivityNotificationDetailBinding? = null
    private val binding get() = _binding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNotificationDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Retrieve data from the intent
        val title = intent.getStringExtra("notificationTitle")
        val detail = intent.getStringExtra("notificationDetail")
        val timestamp = intent.getStringExtra("notificationTimestamp")

        // Update UI with the retrieved data
        binding?.notificationTitle?.text = title
        binding?.notificationDetailMain?.text = detail
        binding?.notificationTimestamp?.text = timestamp

        customizeAppBarTitle()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun customizeAppBarTitle() {
        val materialToolbar: MaterialToolbar? = binding?.notifToAppbar
        val titleText = getString(R.string.notifDetailAppBar)
        val spannableString = SpannableString(titleText)
        val siteIndex = titleText.indexOf("Notification")
        val listIndex = titleText.indexOf("Detail")

        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.navy)),
            siteIndex,
            siteIndex + 6,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.baby_blue)),
            listIndex,
            listIndex + 6,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        materialToolbar?.title = spannableString
        setSupportActionBar(materialToolbar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}