package com.example.cloudrayamobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudrayamobile.data.local.roomDb.SiteListEntity
import com.example.cloudrayamobile.databinding.SiteListItemBinding

class SiteListAdapter : ListAdapter<SiteListEntity, SiteListAdapter.SiteListViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickedCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickedCallback
    }

    inner class SiteListViewHolder(val binding: SiteListItemBinding): RecyclerView.ViewHolder(binding.root) {
        lateinit var getSiteList: SiteListEntity
        fun bind(sites: SiteListEntity){
            getSiteList = sites
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(sites)
            }

            binding.apply {
                tvListSiteName.text = sites.siteName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteListViewHolder {
        val binding = SiteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SiteListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SiteListViewHolder, position: Int) {
        val sites = getItem(position)
        holder.bind(sites)
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: SiteListEntity)
    }

    companion object{
        val DIFF_CALLBACK = object  : DiffUtil.ItemCallback<SiteListEntity>(){
            override fun areItemsTheSame(oldSite: SiteListEntity, newSite: SiteListEntity): Boolean {
                return oldSite == newSite
            }
            override fun areContentsTheSame(oldSite: SiteListEntity, newSite: SiteListEntity): Boolean {
                return oldSite == newSite
            }
        }
    }

}