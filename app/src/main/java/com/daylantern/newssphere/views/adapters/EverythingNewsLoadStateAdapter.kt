package com.daylantern.newssphere.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daylantern.newssphere.databinding.LayoutLoadingBinding

class EverythingNewsLoadStateAdapter() :
    LoadStateAdapter<EverythingNewsLoadStateAdapter.ViewHolder>() {
    
    inner class ViewHolder(
        private val binding: LayoutLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
            }
        }
    }
    
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            LayoutLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}