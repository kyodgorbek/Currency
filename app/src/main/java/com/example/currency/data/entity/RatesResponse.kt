package com.example.currency.data.entity

data class RatesResponse(
    val rates: List<Rate>
)

data class Rate(
    val symbol: String,
    val price: String,
    val isHigher: Boolean = false
)