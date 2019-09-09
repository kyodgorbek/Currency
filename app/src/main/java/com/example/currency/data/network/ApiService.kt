package com.example.currency.data.network

import androidx.lifecycle.LiveData
import com.example.currency.data.entity.RatesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("rates")
    fun rates(): LiveData<ApiResponse<RatesResponse>>
}