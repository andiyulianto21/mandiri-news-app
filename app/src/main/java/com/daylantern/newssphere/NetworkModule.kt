package com.daylantern.newssphere

import com.daylantern.newssphere.retrofit.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Singleton
    @Provides
    fun provideInterceptorApi(): OkHttpClient {
        val apiKeyInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-api-key", "096d7e2e7fee46cda7099d842edf52af")
                .build()
            chain.proceed(request)
        }
        val loggingInterceptor = HttpLoggingInterceptor ()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }
}