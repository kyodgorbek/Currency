package com.example.currency.data.network

import com.example.currency.BuildConfig
import com.example.currency.data.live_data.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object NetworkClient {
    private val  service: ApiService
    init {
          service = provideRetrofitBuilder()
              .baseUrl("https://mt4-api-staging.herokuapp.com/").build().create(ApiService::class.java)
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.tag("OkHttp").d(message) })
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(provideHttpLoggingInterceptor())
            .build()
    }

    private fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())

    }

    fun provideApiService(): ApiService {
        return service
    }
}