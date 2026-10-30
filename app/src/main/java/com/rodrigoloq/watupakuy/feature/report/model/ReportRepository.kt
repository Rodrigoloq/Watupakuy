package com.rodrigoloq.watupakuy.feature.report.model

import com.rodrigoloq.watupakuy.utils.NearbyPlaces
import com.rodrigoloq.watupakuy.utils.Occupancy
import com.rodrigoloq.watupakuy.utils.Tourism
import com.rodrigoloq.watupakuy.utils.TourismBestPlace
import com.rodrigoloq.watupakuy.utils.toNearbyPlaces
import com.rodrigoloq.watupakuy.utils.toOccupancy
import com.rodrigoloq.watupakuy.utils.toTourism
import com.rodrigoloq.watupakuy.data.refrofit.entities.weather.WeatherResponse
import com.rodrigoloq.watupakuy.data.refrofit.services.NearbyPlacesService
import com.rodrigoloq.watupakuy.data.refrofit.services.OccupancyService
import com.rodrigoloq.watupakuy.data.refrofit.services.TourismService
import com.rodrigoloq.watupakuy.data.refrofit.services.WeatherService
import com.rodrigoloq.watupakuy.data.room.dao.OccupancyDao
import com.rodrigoloq.watupakuy.data.room.dao.TourismBestPlaceDao
import com.rodrigoloq.watupakuy.data.room.dao.TourismDao
import com.rodrigoloq.watupakuy.data.room.dao.WeatherDao
import com.rodrigoloq.watupakuy.data.room.entities.occupancy.OccupancyTable
import com.rodrigoloq.watupakuy.data.room.entities.tourism.TourismBestPlaceTable
import com.rodrigoloq.watupakuy.data.room.entities.tourism.TourismTable
import com.rodrigoloq.watupakuy.data.room.entities.weather.WeatherTable
import com.rodrigoloq.watupakuy.utils.PlaceCategoryGroup
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ReportRepository @Inject constructor(
    private val weatherService: WeatherService,
    private val occupancyService: OccupancyService,
    private val tourismService: TourismService,
    private val nearbyPlacesService: NearbyPlacesService,
    private val occupancyDao: OccupancyDao,
    private val tourismDao: TourismDao,
    private val tourismBestPlaceDao: TourismBestPlaceDao,
    private val weatherDao: WeatherDao
){
    suspend fun getWeatherReport(latCoordinates: Double,
                                 lonCoordinates: Double): Result<WeatherResponse> {
        try {
            val weather = weatherService
                .getWeatherPrediction(
                    latCoordinates = latCoordinates,
                    lonCoordinates = lonCoordinates)
            return Result.success(weather)
        } catch (e: CancellationException) {
            throw e
        }catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getOccupancyReport(department: String,
                                   date: String): Result<Occupancy>{
        try {
            val occupancy = occupancyService.getPrediction(
                department = department,
                date = date)
            return Result.success(occupancy.toOccupancy())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getTourismReport(): Result<List<Tourism>>{
        try {
            val tourism = tourismService.getDepartmentInformation()
            return Result.success(tourism.toTourism())
        } catch (e: CancellationException) {
            throw e
        }catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getNearbyPlacesReport(lat: Double,
                                      lon: Double,
                                      department: String,
                                      group: PlaceCategoryGroup): Result<List<NearbyPlaces>>{
        try {
            val categories = group.categories
            val point = "${lon},${lat}"
            val nearbyPlace = nearbyPlacesService.getNearbyPlaces(
                categories = categories,
                filter = "circle:$point,5000",
                bias = "proximity:$point",
            )
            return Result.success(nearbyPlace.toNearbyPlaces(department, group.name))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun doesOccupancyDataExist(department: String, date: String): Boolean {
        return occupancyDao.doesOccupancyDataExist(department, date)
    }

    suspend fun saveOccupancyData(occupancy: OccupancyTable){
        occupancyDao.saveOccupancyData(occupancy)
    }

    suspend fun getOccupancyData(department: String, date: String): OccupancyTable{
        return occupancyDao.getOccupancyData(department,date)
    }

    suspend fun doesTourismDataExist(department: String, date: String): Boolean{
        return tourismDao.doesTourismDataExist(department, date)
    }

    suspend fun saveTourismData(tourism: TourismTable,
                                bestPlaces: List<TourismBestPlace>){
        val result = tourismDao.saveTourismData(tourism)
        if (result > 0){
            bestPlaces.forEach { bestPlace ->
                tourismBestPlaceDao
                    .saveTourismBestPlaceData(TourismBestPlaceTable(
                        tourismId = result,
                        title = bestPlace.title,
                        imageUrl = bestPlace.imageUrl
                    ))
            }
        }
    }

    suspend fun getTourismData(department: String, date: String): TourismTable{
        return tourismDao.getTourismData(department, date)
    }

    suspend fun getTourismBestPlaceData(placeId: Long): List<TourismBestPlaceTable> {
        return tourismBestPlaceDao.getTourismBestPlaceData(placeId)
    }

    suspend fun doesWeatherDataExist(department: String, date: String): Boolean{
        return weatherDao.doesWeatherDataExist(department, date)
    }

    suspend fun saveWeatherData(weather: WeatherTable){
        weatherDao.saveWeatherData(weather)
    }

    suspend fun getWeatherData(department: String, date: String): WeatherTable{
        return weatherDao.getWeatherData(department,date)
    }
}