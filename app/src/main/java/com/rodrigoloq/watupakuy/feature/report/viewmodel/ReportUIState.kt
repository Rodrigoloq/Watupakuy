package com.rodrigoloq.watupakuy.feature.report.viewmodel

import com.rodrigoloq.watupakuy.utils.NearbyPlaces
import com.rodrigoloq.watupakuy.utils.Occupancy
import com.rodrigoloq.watupakuy.utils.Tourism
import com.rodrigoloq.watupakuy.utils.Weather


data class ReportUIState(
    val weather: Weather? = null,
    val tourism: Tourism? = null,
    val occupancy: Occupancy? = null,
    val nearbyPlaces: List<NearbyPlaces>? = null,
    val isWeatherLoading: Boolean = false,
    val isTourismLoading: Boolean = false,
    val isOccupancyLoading: Boolean = false,
    val isInitialNearbyPlacesLoading: Boolean = false,
    val isMenuNearbyPlacesLoading: Boolean = false,
    val weatherErrorMessage: String = "",
    val tourismErrorMessage: String = "",
    val occupancyErrorMessage: String = "",
    val nearbyPlacesErrorMessage: String = ""
)
