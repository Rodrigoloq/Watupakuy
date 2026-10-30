package com.rodrigoloq.watupakuy.data.refrofit.entities.weather

data class WeatherResponse(val latitude: Double,
    val longitude: Double,
    val elevation: Int,
    val monthly: Monthly) {
}