package com.rodrigoloq.watupakuy.feature.report.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.watupakuy.utils.toOccupancy
import com.rodrigoloq.watupakuy.utils.toTourism
import com.rodrigoloq.watupakuy.utils.toWeather
import com.rodrigoloq.watupakuy.data.room.entities.occupancy.OccupancyTable
import com.rodrigoloq.watupakuy.data.room.entities.tourism.TourismTable
import com.rodrigoloq.watupakuy.data.room.entities.weather.WeatherTable
import com.rodrigoloq.watupakuy.feature.report.model.ReportRepository
import com.rodrigoloq.watupakuy.utils.PlaceCategoryGroup
import com.rodrigoloq.watupakuy.utils.RegionMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: ReportRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReportUIState())
    val uiState: StateFlow<ReportUIState> = _uiState.asStateFlow()

    fun getWeatherReport(
        department: String,
        yearMonth: String
    ) {
        _uiState.update { it.copy(isWeatherLoading = true) }
        viewModelScope.launch {
            try {
                if (repository.doesWeatherDataExist(department, yearMonth)){
                    val weatherTable = repository.getWeatherData(department, yearMonth)
                    _uiState.update { it.copy(weather = weatherTable.toWeather()) }
                } else {
                    val result = repository.getWeatherReport(
                        latCoordinates = RegionMapper.getLatitudeFromRegion(department),
                        lonCoordinates = RegionMapper.getLongitudeFromRegion(department)
                    )
                    result.onSuccess { weatherResponse ->
                        val position = weatherResponse.monthly.time.indexOfFirst { date ->
                            date.startsWith(yearMonth)
                        }
                        val weather = weatherResponse.toWeather(position)
                        _uiState.update { it.copy(weather = weather) }
                        repository.saveWeatherData(
                            weather = WeatherTable(
                                department = department,
                                date = yearMonth,
                                latitude = weather.latitude,
                                longitude = weather.longitude,
                                elevation = weather.elevation,
                                temperature_2m_mean = weather.temperature_2m_mean,
                                temperature_max24h_2m_mean = weather.temperature_max24h_2m_mean,
                                temperature_min24h_2m_mean = weather.temperature_min24h_2m_mean,
                                precipitation_mean = weather.precipitation_mean,
                                showers_mean = weather.showers_mean,
                                snowfall_mean = weather.snowfall_mean,
                                snow_depth_mean = weather.snow_depth_mean,
                                cloud_cover_mean = weather.cloud_cover_mean,
                                cloud_cover_low_mean = weather.cloud_cover_low_mean,
                                shortwave_radiation_mean = weather.shortwave_radiation_mean,
                                pressure_msl_mean = weather.pressure_msl_mean,
                                wind_speed_10m_mean = weather.wind_speed_10m_mean,
                                sunshine_duration_mean = weather.sunshine_duration_mean,
                                dew_point_2m_mean = weather.dew_point_2m_mean
                            )
                        )
                    }.onFailure { e ->
                        _uiState.update { it.copy(weather = null,
                            weatherErrorMessage = e.message ?: "") }
                    }
                }
            } catch (e: Exception){
                _uiState.update { it.copy(weather = null,
                    weatherErrorMessage = e.message ?: "") }
            }
            finally {
                _uiState.update { it.copy(isWeatherLoading = false) }
            }
        }
    }

    fun getOccupancyReport(
        department: String,
        date: String
    ) {
        _uiState.update { it.copy(isOccupancyLoading = true) }
        viewModelScope.launch {
            try {
                if (repository.doesOccupancyDataExist(department,date.dropLast(3))){
                    val occupancyTable = repository.getOccupancyData(department, date.dropLast(3))
                    _uiState.update { it.copy(occupancy = occupancyTable.toOccupancy()) }
                } else {
                    val result = repository.getOccupancyReport(
                        department = department,
                        date = date
                    )
                    result.onSuccess { occupancy ->
                        _uiState.update { it.copy(occupancy = occupancy) }
                        repository.saveOccupancyData(
                            occupancy = OccupancyTable(
                                department = department,
                                date = date.dropLast(3),
                                occupancyPercentage = occupancy.occupancyPercentage,
                                clampedToRange = occupancy.clampedToRange,
                                occupancyPercentageHotel = occupancy.occupancyPercentageHotel,
                                occupancyPercentageHostal = occupancy.occupancyPercentageHostal,
                                occupancyPercentageUnclassified = occupancy.occupancyPercentageUnclassified,
                                lastObservedDate = occupancy.lastObservedDate.toString()
                            )
                        )
                    }.onFailure { e ->
                        _uiState.update { it.copy(occupancy = null,
                            occupancyErrorMessage = e.message ?: "") }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(occupancy = null,
                    occupancyErrorMessage = e.message ?: "") }
            }
            finally {
                _uiState.update { it.copy(isOccupancyLoading = false) }
            }
        }
    }

    fun getTourismReport(department: String, date: String) {
        _uiState.update { it.copy(isTourismLoading = true) }
        viewModelScope.launch {
            try {
                if (repository.doesTourismDataExist(department, date)){
                    val tourismTable = repository.getTourismData(department, date)
                    val bestPlaces = repository.getTourismBestPlaceData(tourismTable.id)
                    _uiState.update { it.copy(tourism = tourismTable.toTourism(bestPlaces)) }
                } else {
                    val result = repository.getTourismReport()
                    result.onSuccess { tourismList ->
                        val tourism = tourismList.find {
                            it.title == department
                        }
                        _uiState.update { it.copy(tourism = tourism) }
                        if (tourism != null) {
                            repository.saveTourismData(
                                tourism = TourismTable(
                                    department = department,
                                    date = date,
                                    title = tourism.title,
                                    description = tourism.description
                                ),
                                bestPlaces = tourism.bestPlaces
                            )
                        }
                    }.onFailure {e ->
                        _uiState.update { it.copy(tourism = null,
                            tourismErrorMessage = e.message ?: "") }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(tourism = null,
                    tourismErrorMessage = e.message ?: "") }
            }
            finally {
                _uiState.update { it.copy(isTourismLoading = false) }
            }
        }
    }

    fun getInitialNearbyPlacesReport(
        lat: Double,
        lon: Double,
        department: String,
        group: PlaceCategoryGroup
    ) {
        _uiState.update { it.copy(isInitialNearbyPlacesLoading = true) }
        viewModelScope.launch {
            try {
                val result = repository.getNearbyPlacesReport(lat, lon, department, group)
                result.onSuccess { nearbyPlacesList ->
                    _uiState.update { it.copy(nearbyPlaces = nearbyPlacesList) }
                }.onFailure {e ->
                    _uiState.update { it.copy(nearbyPlaces = null,
                        nearbyPlacesErrorMessage = e.message ?: "") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(nearbyPlaces = null,
                    nearbyPlacesErrorMessage = e.message ?: "") }
            }
            finally {
                _uiState.update { it.copy(isInitialNearbyPlacesLoading = false) }
            }
        }
    }

    fun getMenuNearbyPlacesReport(
        lat: Double,
        lon: Double,
        department: String,
        group: PlaceCategoryGroup
    ) {
        _uiState.update { it.copy(isMenuNearbyPlacesLoading = true) }
        viewModelScope.launch {
            try {
                val result = repository.getNearbyPlacesReport(lat, lon, department, group)
                result.onSuccess { nearbyPlacesList ->
                    _uiState.update { it.copy(nearbyPlaces = nearbyPlacesList) }
                }.onFailure {e ->
                    _uiState.update { it.copy(nearbyPlaces = null,
                        nearbyPlacesErrorMessage = e.message ?: "") }
                }
            } catch (e: Exception){
                _uiState.update { it.copy(nearbyPlaces = null,
                    nearbyPlacesErrorMessage = e.message ?: "") }
            }
            finally {
                _uiState.update { it.copy(isMenuNearbyPlacesLoading = false) }
            }
        }
    }

}