package com.rodrigoloq.watupakuy.refrofit.services

import com.rodrigoloq.watupakuy.BuildConfig
import com.rodrigoloq.watupakuy.refrofit.entities.usefulplaces.UsefulPlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsefulPlacesService {

    @GET("/v2/places")
    suspend fun getUsefulPlaces(@Query("categories") categories: String,
                                @Query("conditions") conditions: String = "named",
                                @Query("filter") filter: String,
                                @Query("bias") bias: String,
                                @Query("lang") lang: String = "en",
                                @Query("limit") limit: Int = 20,
                                @Query("apiKey") apiKey: String = BuildConfig.API_KEY): UsefulPlacesResponse
}