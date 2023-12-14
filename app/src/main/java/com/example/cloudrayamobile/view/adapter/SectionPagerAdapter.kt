package com.example.cloudrayamobile.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter (activity: FragmentActivity, private val fragment: MutableList<Fragment>) : FragmentStateAdapter(activity) {
    var username: String =""

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = fragment[position]
}