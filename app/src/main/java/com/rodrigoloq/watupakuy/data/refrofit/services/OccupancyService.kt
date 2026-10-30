package com.rodrigoloq.watupakuy.data.refrofit.services

import com.rodrigoloq.watupakuy.data.refrofit.entities.occupation.OccupancyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OccupancyService {

    @GET("prediccion")
    suspend fun getPrediction(
        @Query("departamento") department: String,
        @Query("fecha") date: String
    ): OccupancyResponse

}