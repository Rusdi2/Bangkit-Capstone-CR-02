package com.example.cloudrayamobile.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cloudrayamobile.databinding.FragmentNotificationContentBinding
import com.example.cloudrayamobile.view.ViewModelFactory
import com.example.cloudrayamobile.view.main.MainViewModel


class NotificationContentFragment : Fragment() {

    private var _binding: FragmentNotificationContentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationContentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.intoDetail?.setOnClickListener {
            // Handle the click event here
            navigateToDetailActivity()
        }
    }

    private fun navigateToDetailActivity() {
        val notificationTitle = "Notification Title"
        val notificationDetail = "Notification Detail"
        val notificationTimestamp = "2 hours ago"
        val intent = Intent(requireContext(), NotificationDetailActivity::class.java).apply {
            putExtra("notificationTitle", notificationTitle)
            putExtra("notificationDetail", notificationDetail)
            putExtra("notificationTimestamp", notificationTimestamp)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        fun newInstance() = NotificationContentFragment()
    }
}