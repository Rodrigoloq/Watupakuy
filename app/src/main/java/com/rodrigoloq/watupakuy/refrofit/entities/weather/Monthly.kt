package com.rodrigoloq.watupakuy.refrofit.entities.weather

data class Monthly(val time: List<String> = listOf(),
                   val temperature_2m_mean: List<Double> = listOf(),
                   val temperature_max24h_2m_mean: List<Double> = listOf(),
                   val temperature_min24h_2m_mean: List<Double> = listOf(),
                   val precipitation_mean: List<Double> = listOf(),
                   val showers_mean: List<Double> = listOf(),
                   val snowfall_mean: List<Double> = listOf(),
                   val snow_depth_mean: List<Double> = listOf(),
                   val cloud_cover_mean: List<Double> = listOf(),
                   val cloud_cover_low_mean: List<Double> = listOf(),
                   val shortwave_radiation_mean: List<Double> = listOf(),
                   val pressure_msl_mean: List<Double> = listOf(),
                   val wind_speed_10m_mean: List<Double> = listOf(),
                   val sunshine_duration_mean: List<Double> = listOf(),
                   val dew_point_2m_mean: List<Double> = listOf())
