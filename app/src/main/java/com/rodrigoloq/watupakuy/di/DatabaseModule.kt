package com.rodrigoloq.watupakuy.di

import android.content.Context
import androidx.room.Room
import com.rodrigoloq.watupakuy.data.room.AppDatabase
import com.rodrigoloq.watupakuy.data.room.dao.OccupancyDao
import com.rodrigoloq.watupakuy.data.room.dao.TourismBestPlaceDao
import com.rodrigoloq.watupakuy.data.room.dao.TourismDao
import com.rodrigoloq.watupakuy.data.room.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "db_watupakuy"
        ).build()
    }

    @Provides
    fun provideOccupancyDao(
        database: AppDatabase
    ): OccupancyDao {
        return database.occupancyDao()
    }

    @Provides
    fun provideTourismDao(
        database: AppDatabase
    ): TourismDao {
        return database.tourismDao()
    }

    @Provides
    fun provideTourismBestPlaceDao(
        database: AppDatabase
    ): TourismBestPlaceDao {
        return database.tourismBestPlaceDao()
    }

    @Provides
    fun provideWeatherDao(
        database: AppDatabase
    ): WeatherDao {
        return database.weatherDao()
    }
}