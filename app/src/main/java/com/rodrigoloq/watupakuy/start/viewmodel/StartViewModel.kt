package com.rodrigoloq.watupakuy.start.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navDeepLink
import com.rodrigoloq.watupakuy.refrofit.entities.information.InformationResponse
import com.rodrigoloq.watupakuy.refrofit.entities.occupation.OccupationResponse
import com.rodrigoloq.watupakuy.refrofit.entities.usefulplaces.UsefulPlacesResponse
import com.rodrigoloq.watupakuy.refrofit.entities.weather.WeatherResponse
import com.rodrigoloq.watupakuy.room.entities.OccupationPredictions
import com.rodrigoloq.watupakuy.start.model.StartRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.collections.mutableListOf

class StartViewModel : ViewModel() {

    private val repository = StartRepository()
    var isLoading by mutableStateOf(false)

    var InformationData by mutableStateOf<InformationResponse?>(null)

    var weatherData by mutableStateOf<WeatherResponse?>(null)
        private set

    var usefulPlacesData by mutableStateOf<UsefulPlacesResponse?>(null)
        private set

    var occupationData = mutableListOf<OccupationResponse?>()
        private set

    var loadingMessage by mutableStateOf("")


    fun getUsefulPlaces(categories: String,
                        latCoordinates: Double,
                        lonCoordinates: Double){
        isLoading = true
        viewModelScope.launch {
            repository.getUsefulPlaces(categories, latCoordinates, lonCoordinates){ response ->
                if (response != null){
                    usefulPlacesData = response
                }
                Log.i("RLTAG", "getUsefulPlaces: $usefulPlacesData")
            }
            isLoading = false
        }
    }

    fun getAllData(categories: String,
                   latCoordinates: Double,
                   lonCoordinates: Double,
                   city: String){
        isLoading = true
        viewModelScope.launch {

            val messageJob = launch {
                loadingMessage = "Cargando datos..."
                delay(3000)
                loadingMessage = "Procesando información..."
                delay(3000)
                loadingMessage = "Optimizando resultados..."
                delay(3000)
                loadingMessage = "Casi listo..."
            }

            try {
                occupationData.clear()

                val result = repository.doesRegionExists(city)

                if (result) {
                    getPredictionsByRegion(city)
                } else {
                    for (month in 1..9){
                        repository.getOccupationPrediction(city, "2026-0$month-01"){response ->
                            occupationData += response
                        }
                    }
                    for (month in 10..12){
                        repository.getOccupationPrediction(city, "2026-$month-01"){response ->
                            occupationData += response
                        }
                    }
                    insertOccupationDataInDb(city,occupationData)
                }

                repository.getRegionInformation { response ->
                    if (response != null){
                        InformationData = response
                    }
                }
                repository.getWeatherPrediction(latCoordinates, lonCoordinates) { response ->
                    if (response != null){
                        weatherData = response
                    }
                }
                repository.getUsefulPlaces(categories, latCoordinates, lonCoordinates){ response ->
                    if (response != null){
                        usefulPlacesData = response
                    }
                }
            } finally {
                messageJob.cancel()
                isLoading = false
            }
        }
    }

    fun insertOccupationDataInDb(region: String, data: MutableList<OccupationResponse?>){
        viewModelScope.launch {
            val result = repository.doesRegionExists(region)
            if (!result){
                data.forEach { occupationResponse ->
                    repository.addPrediction(OccupationPredictions(departamento = occupationResponse!!.departamento,
                        fecha = occupationResponse.fecha,
                        ocupacionEstimadaconsolidado = occupationResponse.ocupacionEstimadaconsolidado,
                        ocupacionEstimadahotel = occupationResponse.ocupacionEstimadahotel,
                        ocupacionEstimadahostal = occupationResponse.ocupacionEstimadahostal,
                        ocupacionEstimadaotros = occupationResponse.ocupacionEstimadaotros,)){insertResult ->
                        if (!insertResult) Log.e("RLTAG", "insertOccupationDataInDb: error al insertar")
                    }
                }
            }
        }
    }

    fun getPredictionsByRegion(region: String){
        viewModelScope.launch {
            repository.getPredictionsByRegion(region){result ->
                occupationData = result.toMutableList()
            }
        }
    }
}