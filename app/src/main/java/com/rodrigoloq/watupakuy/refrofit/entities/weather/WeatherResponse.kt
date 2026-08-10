package com.rodrigoloq.watupakuy.refrofit.entities.weather

data class WeatherResponse(val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val generationtime_ms: Double = 0.0,
    val utc_offset_seconds: Int = 0,
    val timezone: String = "",
    val timezone_abbreviation: String = "",
    val elevation: Int = 0,
    val monthly_units: MonthlyUnits = MonthlyUnits(),
    val monthly: Monthly = Monthly()) {
}