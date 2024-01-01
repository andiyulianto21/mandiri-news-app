package com.daylantern.newssphere.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.daylantern.newssphere.Constant
import com.daylantern.newssphere.models.NewsResponse
import com.daylantern.newssphere.retrofit.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    
    suspend fun getHeadlineNews(
        country: String,
    ): NewsResponse = newsApi.getHeadlineNews(country)
    
    fun getNews(query: String) = Pager(
        config = PagingConfig(
            pageSize = Constant.PAGING_SIZE,
            prefetchDistance = Constant.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        NewsPagingSource(newsApi, query)
    }.flow
}