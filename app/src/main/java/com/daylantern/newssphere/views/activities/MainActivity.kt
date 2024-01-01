package com.daylantern.newssphere.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.daylantern.newssphere.Constant
import com.daylantern.newssphere.R
import com.daylantern.newssphere.Util
import com.daylantern.newssphere.databinding.ActivityMainBinding
import com.daylantern.newssphere.viewmodels.MainActivityViewModel
import com.daylantern.newssphere.views.adapters.EverythingNewsAdapter
import com.daylantern.newssphere.views.adapters.EverythingNewsLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var everythingNewsAdapter: EverythingNewsAdapter
    private val viewModel: MainActivityViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        everythingNewsAdapter = EverythingNewsAdapter { url -> toDetailActivity(url) }
        
        binding.apply {
            rvEverythingNews.layoutManager = LinearLayoutManager(this@MainActivity)
            rvEverythingNews.adapter = everythingNewsAdapter.withLoadStateHeaderAndFooter(
                footer = EverythingNewsLoadStateAdapter(),
                header = EverythingNewsLoadStateAdapter()
            )
            everythingNewsAdapter.addLoadStateListener { loadState ->
                progresBar.isVisible = loadState.source.refresh is LoadState.Loading
                tvSemuaBerita.isVisible = loadState.source.refresh !is LoadState.Loading
                tvBeritaTerkini.isVisible = loadState.source.refresh !is LoadState.Loading
                cardBeritaTerkini.isVisible = loadState.source.refresh !is LoadState.Loading
                rvEverythingNews.isVisible = loadState.source.refresh !is LoadState.Loading
            }
        }
        fetchEverythingNews()
        viewModel.getHeadlineNews()
        
        viewModel.headlineNews.observe(this@MainActivity) { article ->
            binding.apply {
                article.apply {
                    tvTitle.text = title
                    tvAuthor.text = author
                    tvPublishedDate.text =
                        publishedAt?.let { date -> Util.convertDate(date) }
                    Glide.with(this@MainActivity).load(urlToImage).error(R.drawable.not_found)
                        .into(imgHeadline)
                    
                    cardBeritaTerkini.setOnClickListener {
                        toDetailActivity(url)
                    }
                }
            }
        }
    }
    
    private fun fetchEverythingNews() {
        lifecycleScope.launch {
            viewModel.fetchEverythingNews("Indonesia").collectLatest {
                everythingNewsAdapter.submitData(it)
            }
        }
    }
    
    private fun toDetailActivity(url: String?) {
        val intent = Intent(this@MainActivity, NewsDetail::class.java)
        intent.putExtra(Constant.INTENT_URL, url)
        startActivity(intent)
    }
}