package com.example.cloudrayamobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudrayamobile.data.retrofit.response.ServersItem
import com.example.cloudrayamobile.databinding.VmListItemBinding
import com.example.cloudrayamobile.utils.loadImageBasedOnCountryCode
import com.example.cloudrayamobile.utils.loadImageBasedOnOperatingSystem
import com.example.cloudrayamobile.utils.loadImageBasedOnState

class VmListAdapter : ListAdapter<ServersItem, VmListAdapter.VmListViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickedCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickedCallback
    }

    inner class VmListViewHolder(private val binding: VmListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(serversItem: ServersItem){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(serversItem)
            }

            binding.apply {
                loadImageBasedOnCountryCode(serversItem.countrycode.toString(), ivCountryFlag)
                loadImageBasedOnState(serversItem.state.toString(), ivVmState)
                loadImageBasedOnOperatingSystem(serversItem.templateLabel.toString(), ivVmOs)

                tvHostName.text = serversItem.name
                tvLocationName.text = serversItem.location
                tvLaunchDate.text = serversItem.launchDate
                tvVmState.text = serversItem.state
                tvVmOs.text = serversItem.templateLabel
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VmListViewHolder {
        val binding = VmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VmListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VmListViewHolder, position: Int) {
        val vmList = getItem(position)
        holder.bind(vmList)
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: ServersItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ServersItem>() {
            override fun areItemsTheSame(oldVm: ServersItem, newVm: ServersItem): Boolean {
                return oldVm == newVm
            }

            override fun areContentsTheSame(oldVm: ServersItem, newVm: ServersItem): Boolean {
                return oldVm == newVm
            }
        }
    }

}