package com.example.cloudrayamobile.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cloudrayamobile.R
import com.example.cloudrayamobile.databinding.FragmentNotificationsBinding
import com.example.cloudrayamobile.view.ViewModelFactory
import com.example.cloudrayamobile.view.adapter.SectionPagerAdapter
import com.example.cloudrayamobile.view.main.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notificationContentFragment = mutableListOf<Fragment>(
            NotificationContentFragment.newInstance(),
            NotificationContentFragment.newInstance()
        )

        val titleTabNotificationContentFragment = mutableListOf(
            getString(R.string.notifications_title), getString(R.string.alert)
        )

        val sectionPagerAdapter = SectionPagerAdapter(requireActivity(), notificationContentFragment)
        _binding?.viewPager?.adapter = sectionPagerAdapter

        TabLayoutMediator(_binding!!.tabLayout, _binding?.viewPager!!){ tab, position ->
            tab.text = titleTabNotificationContentFragment[position]
        }.attach()

        _binding!!.tabLayout.getTabAt(0)?.select()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}