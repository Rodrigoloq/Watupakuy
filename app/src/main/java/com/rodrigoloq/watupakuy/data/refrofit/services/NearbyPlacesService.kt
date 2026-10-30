package com.rodrigoloq.watupakuy.data.refrofit.services

import com.rodrigoloq.watupakuy.BuildConfig
import com.rodrigoloq.watupakuy.data.refrofit.entities.nearbyplaces.NearbyPlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NearbyPlacesService {

    @GET("/v2/places")
    suspend fun getNearbyPlaces(@Query("categories", encoded = true) categories: String,
                                @Query("conditions") conditions: String = "named",
                                @Query("filter", encoded = true) filter: String,
                                @Query("bias", encoded = true) bias: String,
                                @Query("lang") lang: String = "en",
                                @Query("limit") limit: Int = 20,
                                @Query("apiKey") apiKey: String = BuildConfig.API_KEY): NearbyPlacesResponse
}