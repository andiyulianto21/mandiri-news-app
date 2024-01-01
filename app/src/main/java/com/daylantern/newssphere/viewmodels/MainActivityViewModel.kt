package com.daylantern.newssphere.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import androidx.paging.map
import com.daylantern.newssphere.Constant
import com.daylantern.newssphere.models.Article
import com.daylantern.newssphere.repositories.NewsPagingSource
import com.daylantern.newssphere.repositories.NewsRepository
import com.daylantern.newssphere.retrofit.NewsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val newsRepo: NewsRepository): ViewModel() {

    private var _headlineNews = MutableLiveData<Article>()
    val headlineNews: LiveData<Article> get() = _headlineNews
    
    fun fetchEverythingNews(query: String): Flow<PagingData<Article>> {
        return newsRepo.getNews(query).cachedIn(viewModelScope)
    }
    
    fun getHeadlineNews() {
        viewModelScope.launch {
            val result = newsRepo.getHeadlineNews("id")
            _headlineNews.value = result.articles.first()
        }
    }
}