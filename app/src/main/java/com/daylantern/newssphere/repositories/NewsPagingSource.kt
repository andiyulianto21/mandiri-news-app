package com.daylantern.newssphere.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.daylantern.newssphere.Constant
import com.daylantern.newssphere.models.Article
import com.daylantern.newssphere.retrofit.NewsApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val newsApi: NewsApi,
    private val query: String
): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val pageIndex = params.key ?: Constant.STARTING_PAGE
            val response = newsApi.getEverythingNews(query, pageIndex, Constant.PAGING_SIZE)
            val data = response.articles
            
            LoadResult.Page(
                data = data,
                prevKey = if(pageIndex == Constant.STARTING_PAGE) null else pageIndex - 1,
                nextKey = if(response.status == "error") null else pageIndex + 1
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}