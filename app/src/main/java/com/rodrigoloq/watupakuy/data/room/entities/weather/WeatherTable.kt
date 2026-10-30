package com.rodrigoloq.watupakuy.data.room.entities.weather

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "weather", indices = [Index("department")])
data class WeatherTable(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                        val department: String,
                        val date: String,
                        val latitude: Double,
                        val longitude: Double,
                        val elevation: Int,
                        val temperature_2m_mean: Double,
                        val temperature_max24h_2m_mean: Double,
                        val temperature_min24h_2m_mean: Double,
                        val precipitation_mean: Double,
                        val showers_mean: Double,
                        val snowfall_mean: Double,
                        val snow_depth_mean: Double,
                        val cloud_cover_mean: Double,
                        val cloud_cover_low_mean: Double,
                        val shortwave_radiation_mean: Double,
                        val pressure_msl_mean: Double,
                        val wind_speed_10m_mean: Double,
                        val sunshine_duration_mean: Double,
                        val dew_point_2m_mean: Double)
