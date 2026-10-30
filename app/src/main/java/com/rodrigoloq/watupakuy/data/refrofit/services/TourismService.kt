package com.rodrigoloq.watupakuy.data.refrofit.services

import com.rodrigoloq.watupakuy.data.refrofit.entities.information.TourismResponse
import retrofit2.http.GET

interface TourismService {

    @GET("/c165faf9f85c929f1087")
    suspend fun getDepartmentInformation(): TourismResponse
}