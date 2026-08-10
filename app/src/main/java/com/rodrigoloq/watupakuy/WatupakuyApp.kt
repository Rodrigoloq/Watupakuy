package com.rodrigoloq.watupakuy

import android.app.Application
import androidx.room.Room
import com.rodrigoloq.watupakuy.room.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WatupakuyApp: Application() {
    companion object{
        lateinit var roomDatabase: AppDatabase
        lateinit var occupationApi: Retrofit
        lateinit var weatherApi: Retrofit
        lateinit var usefulPlacesApi: Retrofit
        lateinit var informationApi: Retrofit
    }

    private fun retrofitBuilder(baseUrl: String): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        roomDatabase = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "watupakuy_db").build()
        occupationApi = retrofitBuilder("https://api-ocupacion-hotel.onrender.com")
        weatherApi = retrofitBuilder("https://seasonal-api.open-meteo.com")
        usefulPlacesApi = retrofitBuilder("https://api.geoapify.com")
        informationApi = retrofitBuilder("https://api.npoint.io")
    }
}