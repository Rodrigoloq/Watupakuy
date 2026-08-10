package com.rodrigoloq.watupakuy.start.model

import android.util.Log
import com.rodrigoloq.watupakuy.WatupakuyApp
import com.rodrigoloq.watupakuy.refrofit.entities.information.InformationResponse
import com.rodrigoloq.watupakuy.refrofit.entities.occupation.OccupationResponse
import com.rodrigoloq.watupakuy.refrofit.entities.usefulplaces.UsefulPlacesResponse
import com.rodrigoloq.watupakuy.refrofit.entities.weather.WeatherResponse
import com.rodrigoloq.watupakuy.refrofit.services.InformationService
import com.rodrigoloq.watupakuy.refrofit.services.OccupationService
import com.rodrigoloq.watupakuy.refrofit.services.UsefulPlacesService
import com.rodrigoloq.watupakuy.refrofit.services.WeatherService
import com.rodrigoloq.watupakuy.room.OccupationPredictionsDAO
import com.rodrigoloq.watupakuy.room.entities.OccupationPredictions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StartRepository {

    private val occupationPredictionsDAO: OccupationPredictionsDAO by lazy { WatupakuyApp
        .roomDatabase.occupationPredictionsDAO() }
    private val weatherService: WeatherService by lazy {
        WatupakuyApp.weatherApi.create(WeatherService::class.java) }

    private val usefulPlacesService: UsefulPlacesService by lazy {
        WatupakuyApp.usefulPlacesApi.create(UsefulPlacesService::class.java)
    }

    private val occupationService: OccupationService by lazy {
        WatupakuyApp.occupationApi.create(OccupationService::class.java)
    }

    private val informationService: InformationService by lazy {
        WatupakuyApp.informationApi.create(InformationService::class.java)
    }

    suspend fun getWeatherPrediction(latCoordinates: Double,
                                     lonCoordinates: Double,
                                     onResult:(WeatherResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = weatherService.getWeatherPrediction(latCoordinates, lonCoordinates)
                onResult(response)
            } catch (e: Exception){
                Log.e("RLTAG", "getWeatherPrediction: ${e.message}")
                onResult(null)
            }
        }

    suspend fun getUsefulPlaces(categories: String,
                                latCoordinates: Double,
                                lonCoordinates: Double,
                                onResult: (UsefulPlacesResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = usefulPlacesService.getUsefulPlaces(
                    categories = categories,
                    filter = "circle:$lonCoordinates,$latCoordinates,5000",
                    bias = "proximity:$lonCoordinates,$latCoordinates"
                )
                onResult(response)
            } catch (e: Exception){
                Log.e("RLTAG", "getUsefulPlaces: ${e.message}")
                onResult(null)
            }
    }

    suspend fun getOccupationPrediction(city: String,
                                        date: String,
                                        onResult: (OccupationResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = occupationService.getOccupationPrediction(city,date)
                onResult(response)
            } catch (e: Exception){
                Log.e("RLTAG", "getOccupationPrediction: ${e.message}")
                onResult(null)
            }
        }

    suspend fun getRegionInformation(onResult: (InformationResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = informationService.getRegionInformation()
                onResult(response)
            } catch (e: Exception){
                Log.e("RLTAG", "getRegionInformation: ${e.message}")
                onResult(null)
            }
    }

    suspend fun addPrediction(prediction: OccupationPredictions,
                              onResult:(Boolean) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            val newId = occupationPredictionsDAO.addPrediction(prediction)
            onResult(newId > 0)
        } catch (e: Exception){
            Log.e("RLTAG", "addPrediction: ${e.message}")
            onResult(false)
        }
    }

    suspend fun doesRegionExists(region: String): Boolean{
        try {
            val result = occupationPredictionsDAO.doesRegionExists(region)
            return result
        } catch (e: Exception) {
            Log.e("RLTAG", "doesRegionExists: ${e.message}")
            return true
        }
    }

    suspend fun getPredictionsByRegion(region: String,
                                       onResult: (List<OccupationResponse>) -> Unit) =
        withContext(Dispatchers.IO){
        val result = occupationPredictionsDAO.getPredictionsByRegion(region)

        if (result.isNotEmpty()){
            onResult(result)
        }
    }

}