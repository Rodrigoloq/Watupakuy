package com.rodrigoloq.watupakuy.data.refrofit.services

import com.rodrigoloq.watupakuy.data.refrofit.entities.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("/v1/seasonal")
    suspend fun getWeatherPrediction(@Query("latitude") latCoordinates: Double,
                                     @Query("longitude") lonCoordinates: Double,
                                     @Query("monthly") monthly: String = "temperature_2m_mean,temperature_max24h_2m_mean," + "temperature_min24h_2m_mean,precipitation_mean,showers_mean,snowfall_mean,snow_depth_mean,cloud_cover_mean,cloud_cover_low_mean,shortwave_radiation_mean,pressure_msl_mean,wind_speed_10m_mean,sunshine_duration_mean,dew_point_2m_mean",
                                     @Query("timezone") timezone: String = "auto",
                                     @Query("past_days") pastDays: Int = 156,
                                     @Query("forecast_days") forecastDays: Int = 217): WeatherResponse
}