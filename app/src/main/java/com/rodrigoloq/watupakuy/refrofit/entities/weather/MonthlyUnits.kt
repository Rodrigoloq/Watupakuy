package com.rodrigoloq.watupakuy.refrofit.entities.weather

data class MonthlyUnits(val time: String = "",
    val temperature_2m_mean: String = "",
    val temperature_max24h_2m_mean: String = "",
    val temperature_min24h_2m_mean: String = "",
    val precipitation_mean: String = "",
    val showers_mean: String = "",
    val snowfall_mean: String = "",
    val snow_depth_mean: String = "",
    val cloud_cover_mean: String = "",
    val cloud_cover_low_mean: String = "",
    val shortwave_radiation_mean: String = "",
    val pressure_msl_mean: String = "",
    val wind_speed_10m_mean: String = "",
    val sunshine_duration_mean: String = "",
    val dew_point_2m_mean: String = ""
){}

