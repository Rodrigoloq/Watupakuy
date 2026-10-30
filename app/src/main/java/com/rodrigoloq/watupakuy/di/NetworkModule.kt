package com.rodrigoloq.watupakuy.di

import com.rodrigoloq.watupakuy.data.refrofit.services.NearbyPlacesService
import com.rodrigoloq.watupakuy.data.refrofit.services.OccupancyService
import com.rodrigoloq.watupakuy.data.refrofit.services.TourismService
import com.rodrigoloq.watupakuy.data.refrofit.services.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    fun retrofit(baseUrl: String): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesTourismApi(): TourismService{
        return retrofit("https://api.npoint.io").create(TourismService::class.java)
    }

    @Provides
    @Singleton
    fun providesOccupancyApi(): OccupancyService{
        return retrofit("https://api-ocupacion-habitaciones-production.up.railway.app").create(OccupancyService::class.java)
    }

    @Provides
    @Singleton
    fun providesWeatherApi(): WeatherService{
        return retrofit("https://seasonal-api.open-meteo.com").create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun providesNearbyPlacesApi(): NearbyPlacesService{
        return retrofit("https://api.geoapify.com").create(NearbyPlacesService::class.java)
    }


}