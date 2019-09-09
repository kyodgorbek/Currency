package com.example.currency.view_model

import androidx.lifecycle.*
import com.example.currency.data.entity.RatesResponse
import com.example.currency.data.network.ApiResponse
import com.example.currency.data.network.NetworkClient
import timber.log.Timber
import java.util.*

class CurrencyViewModel: ViewModel() {

    private val request = MutableLiveData<Boolean>()

    init {
        Timber.e("Initializing view model")
    }

    val rates: LiveData<ApiResponse<RatesResponse>> =  Transformations.switchMap(request) {
        NetworkClient.provideApiService().rates()
    }

    fun getRates() {
        Timer().scheduleAtFixedRate(object: TimerTask(){
            override fun run() {
                Timber.e("Request to rate")
                request.postValue(true)
            }
        },0, 10000)
    }

}