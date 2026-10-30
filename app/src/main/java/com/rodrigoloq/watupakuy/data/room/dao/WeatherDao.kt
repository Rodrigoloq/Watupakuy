package com.rodrigoloq.watupakuy.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rodrigoloq.watupakuy.data.room.entities.occupancy.OccupancyTable
import com.rodrigoloq.watupakuy.data.room.entities.weather.WeatherTable

@Dao
interface WeatherDao {

    @Insert
    suspend fun saveWeatherData(weather: WeatherTable): Long

    @Query("SELECT EXISTS(SELECT 1 FROM weather WHERE department = :department AND date = :date)")
    suspend fun doesWeatherDataExist(department: String, date: String): Boolean

    @Query("SELECT * FROM weather WHERE department = :department AND date = :date")
    suspend fun getWeatherData(department: String, date: String): WeatherTable

}