package com.rodrigoloq.watupakuy.refrofit.services

import com.rodrigoloq.watupakuy.refrofit.entities.occupation.OccupationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OccupationService {

    @GET("/prediccion")
    suspend fun getOccupationPrediction(@Query("ciudad") city: String,
                                        @Query("fecha") date: String): OccupationResponse

}