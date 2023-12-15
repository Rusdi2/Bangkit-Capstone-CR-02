package com.example.cloudrayamobile.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cloudrayamobile.R

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun loadImageBasedOnOperatingSystem(templateLabel: String, imageView: ImageView) {

    val drawableResId = when (templateLabel) {
        "KEMP-VLM-7.2.54.3" -> R.drawable.img_os_kemp
        "KEMP VLM" -> R.drawable.img_os_kemp
        "KEMP-VLM-SPLA-7.2.54.3" -> R.drawable.img_os_kemp
        "Kali 2021 Minimal" -> R.drawable.img_os_kali
        "Fedora 36" -> R.drawable.img_os_fedora
        "Debian 9 v03.22" -> R.drawable.img_os_debian
        "Debian 12" -> R.drawable.img_os_debian
        "Debian 11 v03.22" -> R.drawable.img_os_debian
        "Debian 10 v03.22" -> R.drawable.img_os_debian
        "CentOS Stream 8 v03.22" -> R.drawable.img_os_centos
        "Centos 7 with cPanel v03.22" -> R.drawable.img_os_centos
        "CentOS 7 v03.22" -> R.drawable.img_os_centos
        "Alpine Linux 3.15 v03.22" -> R.drawable.img_os_alpine
        "AlmaLinux 8 v03.22" -> R.drawable.img_os_alma_linux
        "Windows 2019 Std with SQL 2019 Std" -> R.drawable.img_os_windows
        "Windows 2019 Standard v03.22" -> R.drawable.img_os_windows
        "Windows 2016 Std with SQL 2016 Std" -> R.drawable.img_os_windows
        "Windows 2016 Standard v03.22" -> R.drawable.img_os_windows
        "Ubuntu 22.04 v05.22" -> R.drawable.img_os_ubuntu
        "Ubuntu 20.04 with cPanel" -> R.drawable.img_os_ubuntu
        "Ubuntu 20.04 v03.22" -> R.drawable.img_os_ubuntu
        "Ubuntu 18.04 v03.22" -> R.drawable.img_os_ubuntu
        "Ubuntu 16.04 v03.22" -> R.drawable.img_os_ubuntu
        "Rocky Linux 9 v09.22" -> R.drawable.img_os_rocky_linux
        "Rocky Linux 8 v03.22" -> R.drawable.img_os_rocky_linux
        "Redhat Enterprise (RHEL) 7.8" -> R.drawable.img_os_redhat_enterprise
        "Oracle Linux 8.2" -> R.drawable.img_os_orecle_linux
        "OpenSUSE 15.2" -> R.drawable.img_os_opensus
        "MikrotikCHR-7.7" -> R.drawable.img_os_mikrotik_chr
        "MikrotikCHR 6.48.6" -> R.drawable.img_os_mikrotik_chr
        else -> {
            R.drawable.img_os_other}
    }
    loadImage(drawableResId, imageView)
}

fun loadImageBasedOnCountryCode(countryCode: String, imageView: ImageView) {
    val drawableResId = when (countryCode) {
        "id" -> R.drawable.img_flag_idn
        "us" -> R.drawable.img_flag_usa
        else -> {
            R.color.white}
    }
    loadImage(drawableResId, imageView)
}

fun loadImageBasedOnState(state: String, imageView: ImageView) {
    val drawableResId = when (state) {
        "Running" -> R.drawable.img_running_vm
        "Stopped" -> R.drawable.img_stopped_vm_detail
        else -> R.drawable.img_other_vm_state
    }
    loadImage(drawableResId, imageView)
}

private fun loadImage(drawableResId: Int, imageView: ImageView) {
    Glide.with(imageView)
        .load(drawableResId)
        .skipMemoryCache(true)
        .centerCrop()
        .into(imageView)
}

fun showLoading(progressBar: ProgressBar, isLoading: Boolean) {
    progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
}


