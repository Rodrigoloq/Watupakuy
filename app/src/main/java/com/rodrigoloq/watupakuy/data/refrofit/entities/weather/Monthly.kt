package com.rodrigoloq.watupakuy.data.refrofit.entities.weather

import com.google.gson.annotations.SerializedName

data class Monthly(val time: List<String> = listOf(),
                   @SerializedName("temperature_2m_mean") val temperature: List<Double> = listOf(),
                   @SerializedName("temperature_max24h_2m_mean") val temperatureMax: List<Double> = listOf(),
                   @SerializedName("temperature_min24h_2m_mean") val temperatureMin: List<Double> = listOf(),
                   @SerializedName("precipitation_mean") val precipitation: List<Double> = listOf(),
                   @SerializedName("showers_mean") val showers: List<Double> = listOf(),
                   @SerializedName("snowfall_mean") val snowfall: List<Double> = listOf(),
                   @SerializedName("snow_depth_mean") val snowDepth: List<Double> = listOf(),
                   @SerializedName("cloud_cover_mean") val cloudCover: List<Double> = listOf(),
                   @SerializedName("cloud_cover_low_mean") val cloudCoverLow: List<Double> = listOf(),
                   @SerializedName("shortwave_radiation_mean") val shortwaveRadiation: List<Double> = listOf(),
                   @SerializedName("pressure_msl_mean") val pressureMsl: List<Double> = listOf(),
                   @SerializedName("wind_speed_10m_mean") val windSpeed: List<Double> = listOf(),
                   @SerializedName("sunshine_duration_mean") val sunshineDuration: List<Double> = listOf(),
                   @SerializedName("dew_point_2m_mean") val dewPoint: List<Double> = listOf())
