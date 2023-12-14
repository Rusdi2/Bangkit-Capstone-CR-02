package com.example.cloudrayamobile.view.siteList

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudrayamobile.R
import com.example.cloudrayamobile.data.local.roomDb.SiteListEntity
import com.example.cloudrayamobile.data.local.sharedPref.TokenAuth
import com.example.cloudrayamobile.data.model.TokenRequestData
import com.example.cloudrayamobile.data.retrofit.result.ResultState
import com.example.cloudrayamobile.databinding.ActivitySiteListBinding
import com.example.cloudrayamobile.utils.Event
import com.example.cloudrayamobile.utils.showLoading
import com.example.cloudrayamobile.utils.showToast
import com.example.cloudrayamobile.view.ViewModelFactory
import com.example.cloudrayamobile.view.adapter.SiteListAdapter
import com.example.cloudrayamobile.view.main.MainActivity
import com.example.cloudrayamobile.view.registerList.RegisterList
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class SiteList : AppCompatActivity() {
    private var _binding: ActivitySiteListBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<SiteListViewModel> {
        ViewModelFactory.getInstance(this)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySiteListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val layoutManager = LinearLayoutManager(this)
        binding?.rvSiteList?.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvSiteList?.addItemDecoration(itemDecoration)

        //Get All Site
        viewModel.getAllSiteList().observe(this){site ->
            setSiteList(site)
        }

        customizeAppBarTitle()
        initAction()
    }

    private fun setSiteList(site: List<SiteListEntity>){
        val adapter = SiteListAdapter()
        adapter.submitList(site)
        binding?.rvSiteList?.adapter = adapter

        adapter.setOnItemClickCallback(object : SiteListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: SiteListEntity) {
                val appKey = data.appKey.toString()
                val secretKey = data.secretKey.toString()

                val tokenRequestData = TokenRequestData(appKey, secretKey)

                viewModel.siteLogin(tokenRequestData).observe(this@SiteList){result ->
                    if (result != null){
                        when(result){
                            is ResultState.Loading -> {
                                showLoading(binding!!.progressBar, true)
                            }
                            is ResultState.Success -> {
                                val getTokenResponse = result.data.data
                                val tokenResponseData = TokenAuth(
                                    getTokenResponse?.bearerToken.toString(),
                                    getTokenResponse?.username.toString(),
                                )
                                viewModel.saveSiteAuth(tokenResponseData)
                                showLoading(binding!!.progressBar, false)
                                showToast(this@SiteList, resources.getString(R.string.welcome_home, getTokenResponse?.username))
                                startActivity(Intent(this@SiteList, MainActivity::class.java))
                            }
                            is ResultState.Error -> {
                                showLoading(binding!!.progressBar, false)
                                showToast(this@SiteList, result.error)
                            }
                        }
                    }
                }
            }
        })
        viewModel.snackbarText.observe(this@SiteList, Observer(this@SiteList::showSnackBar))
        binding?.lyAddSite?.setOnClickListener {
            startActivity(Intent(this@SiteList, RegisterList::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun customizeAppBarTitle() {
        val materialToolbar: MaterialToolbar? = binding?.siteListToAppBar
        val titleText = getString(R.string.site_list_bold)
        val spannableString = SpannableString(titleText)
        val siteIndex = titleText.indexOf("Site")
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

        materialToolbar?.title = spannableString

        setSupportActionBar(materialToolbar)
    }

    private fun showSnackBar(eventMessage: Event<Int>) {
        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            findViewById(R.id.activity_site_list),
            getString(message),
            Snackbar.LENGTH_SHORT
        ).setAction("Undo"){
            viewModel.insertSite(viewModel.undo.value?.getContentIfNotHandled() as SiteListEntity)
        }.show()
    }

    private fun initAction(){
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val siteListEntity = (viewHolder as SiteListAdapter.SiteListViewHolder).getSiteList
                viewModel.deleteSite(siteListEntity)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding?.rvSiteList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}