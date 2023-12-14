package com.example.cloudrayamobile.ui.profile

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.cloudrayamobile.R
import com.example.cloudrayamobile.data.retrofit.result.ResultState
import com.example.cloudrayamobile.databinding.FragmentProfileBinding
import com.example.cloudrayamobile.utils.showLoading
import com.example.cloudrayamobile.utils.showToast
import com.example.cloudrayamobile.view.ViewModelFactory
import com.example.cloudrayamobile.view.main.MainViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDetailUserData()
    }

    private fun getDetailUserData(){
        viewModel.getDetailUser().observe(requireActivity()){ result ->
            when (result){
                is ResultState.Loading -> {
                    showLoading(_binding!!.progressBar, true)
                }
                is ResultState.Success -> {
                    val detailUserDataList = result.data
                    if (detailUserDataList.isNotEmpty()){
                        val detailUser = detailUserDataList[0]
                        _binding?.apply {
                            showLoading(progressBar, false)
                            lyProfileContent.visibility = View.VISIBLE

                            linearLayoutBackgroundState(detailUser.statusWords.toString(), lyProfileState)
                            updateTextStateColor(detailUser.statusWords.toString(), tvProfileState)

                            profileUsername.text = detailUser.username
                            tvFirstName.text = detailUser.firstname
                            tvLastName.text = detailUser.lastname
                            tvProfileEmail.text = detailUser.email
                            tvProfileState.text = detailUser.statusWords
                        }
                    }
                }
                is ResultState.Error -> {
                    showToast(requireContext(), result.error)
                }
            }
        }
    }

    private fun linearLayoutBackgroundState(state: String, linearLayout: LinearLayout) {
        val colorResId = when (state) {
            getString(R.string.active) -> R.color.success_light
            else -> R.color.danger_light
        }
        setLinearLayoutBackgroundColor(colorResId, linearLayout)
    }

    private fun setLinearLayoutBackgroundColor(color: Int, linearLayout: LinearLayout) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(linearLayout.context, color))
    }

    private fun updateTextStateColor(state: String, textView: TextView) {
            val drawableResId = when (state) {
                getString(R.string.active) -> R.color.success_active
                else -> R.color.danger_default
            }
            setTextStateColor(drawableResId, textView)
    }

    private fun setTextStateColor(colorResId: Int, textView: TextView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(ContextCompat.getColor(textView.context, colorResId))
        } else {
            @Suppress("DEPRECATION")
            textView.setTextColor(resources.getColor(colorResId))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}