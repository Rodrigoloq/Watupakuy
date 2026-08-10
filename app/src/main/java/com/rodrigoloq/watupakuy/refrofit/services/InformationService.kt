package com.rodrigoloq.watupakuy.refrofit.services

import com.rodrigoloq.watupakuy.refrofit.entities.information.InformationResponse
import retrofit2.http.GET

interface InformationService {

    @GET("/27199cbc0b27eb1e610d")
    suspend fun getRegionInformation(): InformationResponse
}