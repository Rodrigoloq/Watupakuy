package com.rodrigoloq.watupakuy.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigoloq.watupakuy.data.room.dao.OccupancyDao
import com.rodrigoloq.watupakuy.data.room.dao.TourismBestPlaceDao
import com.rodrigoloq.watupakuy.data.room.dao.TourismDao
import com.rodrigoloq.watupakuy.data.room.dao.WeatherDao
import com.rodrigoloq.watupakuy.data.room.entities.occupancy.OccupancyTable
import com.rodrigoloq.watupakuy.data.room.entities.tourism.TourismBestPlaceTable
import com.rodrigoloq.watupakuy.data.room.entities.tourism.TourismTable
import com.rodrigoloq.watupakuy.data.room.entities.weather.WeatherTable

@Database(
    entities = [OccupancyTable::class,
        TourismTable::class,
        TourismBestPlaceTable::class,
        WeatherTable::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun occupancyDao(): OccupancyDao
    abstract fun tourismDao(): TourismDao
    abstract fun tourismBestPlaceDao(): TourismBestPlaceDao
    abstract fun weatherDao(): WeatherDao
}