package com.daylantern.newssphere.retrofit

import com.daylantern.newssphere.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getHeadlineNews(
        @Query("country") country: String,
    ): NewsResponse
    
    @GET("everything")
    suspend fun getEverythingNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): NewsResponse

}