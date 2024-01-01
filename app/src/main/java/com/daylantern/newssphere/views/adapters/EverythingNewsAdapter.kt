package com.daylantern.newssphere.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daylantern.newssphere.R
import com.daylantern.newssphere.Util
import com.daylantern.newssphere.databinding.CardNewsBinding
import com.daylantern.newssphere.models.Article

class EverythingNewsAdapter(private val onClick: (url: String?) -> Unit): PagingDataAdapter<Article, EverythingNewsAdapter.ViewHolder>(DiffUtilCallback) {
    
    inner class ViewHolder(private val binding: CardNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Article){
            binding.apply {
                Glide.with(itemView).load(data.urlToImage).placeholder(R.drawable.not_found).into(imgNews)
                tvTitle.text = data.title
                tvAuthor.text = data.source?.name ?: "-"
                tvDate.text = data.publishedAt?.let { Util.convertDate(it) }
            }
            itemView.setOnClickListener { onClick(data.url) }
        }
    }
    
    object DiffUtilCallback: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EverythingNewsAdapter.ViewHolder {
        return ViewHolder(CardNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}