package com.rodrigoloq.watupakuy.utils

import androidx.compose.ui.graphics.painter.Painter
import com.rodrigoloq.watupakuy.refrofit.entities.weather.MonthlyUnits

data class WeatherForecastItem(
    val date: String = "",
    val temperature_2m_mean: Double = 0.0,
    val temperature_max24h_2m_mean: Double = 0.0,
    val temperature_min24h_2m_mean: Double = 0.0,
    val showers_mean: Double = 0.0,
    val cloud_cover_mean: Double = 0.0,
    val shortwave_radiation_mean: Double = 0.0,
    val pressure_msl_mean: Double = 0.0,
    val wind_speed_10m_mean: Double = 0.0,
    val sunshine_duration_mean: Double = 0.0,
    val dew_point_2m_mean: Double = 0.0,
    val snowfall_mean: Double = 0.0,
    val monthlyUnits: MonthlyUnits = MonthlyUnits(),
    val time: String = ""
)
