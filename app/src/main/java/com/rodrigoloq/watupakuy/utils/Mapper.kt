package com.rodrigoloq.watupakuy.utils

import com.rodrigoloq.watupakuy.data.refrofit.entities.information.TourismResponse
import com.rodrigoloq.watupakuy.data.refrofit.entities.nearbyplaces.NearbyPlacesResponse
import com.rodrigoloq.watupakuy.data.refrofit.entities.occupation.OccupancyResponse
import com.rodrigoloq.watupakuy.data.refrofit.entities.weather.WeatherResponse
import com.rodrigoloq.watupakuy.data.room.entities.occupancy.OccupancyTable
import com.rodrigoloq.watupakuy.data.room.entities.tourism.TourismBestPlaceTable
import com.rodrigoloq.watupakuy.data.room.entities.tourism.TourismTable
import com.rodrigoloq.watupakuy.data.room.entities.weather.WeatherTable
import java.time.YearMonth



fun getMonthAndYear(fullDate: String): YearMonth{
    val year = fullDate.substring(0,4).toInt()
    val month = fullDate.substring(5,7).toInt()
    return YearMonth.of(year,month)
}

data class Occupancy(val regionName: String,
                     val date: YearMonth,
                     val occupancyPercentage: Double,
                     val clampedToRange: Boolean,
                     val lastObservedDate: YearMonth,
                     val occupancyPercentageHotel: Double,
                     val occupancyPercentageHostal: Double,
                     val occupancyPercentageUnclassified: Double)

fun OccupancyResponse.toOccupancy(): Occupancy{
    return Occupancy(
        regionName = departamento,
        date = getMonthAndYear(fecha),
        occupancyPercentage = totalPercent,
        clampedToRange = clampedToRange,
        lastObservedDate = getMonthAndYear(lastObservedDate),
        occupancyPercentageHotel = byClass[0].percent,
        occupancyPercentageHostal = byClass[1].percent,
        occupancyPercentageUnclassified = byClass[2].percent,
    )
}

fun OccupancyTable.toOccupancy(): Occupancy{
    return Occupancy(
        regionName = department,
        date = YearMonth.parse(date),
        occupancyPercentage = occupancyPercentage,
        clampedToRange = clampedToRange,
        lastObservedDate = YearMonth.parse(lastObservedDate),
        occupancyPercentageHotel = occupancyPercentageHotel,
        occupancyPercentageHostal = occupancyPercentageHostal,
        occupancyPercentageUnclassified = occupancyPercentageUnclassified
    )
}

data class Tourism(
    val title: String,
    val description: String,
    val bestPlaces: List<TourismBestPlace>
)

data class TourismBestPlace(
    val title: String,
    val imageUrl: String
)

fun TourismResponse.toTourism() = regions.map {region ->
    Tourism(
        title = region.title,
        description = region.description,
        bestPlaces = region.bestPlaces.map { bestPlace ->
            TourismBestPlace(
                title = bestPlace.title,
                imageUrl = bestPlace.image
            )
        }
    )
}

fun TourismTable.toTourism(bestPlaces: List<TourismBestPlaceTable>): Tourism {
    return Tourism(
        title = title,
        description = description,
        bestPlaces = bestPlaces.map { bestPlaces ->
            TourismBestPlace(
                title = bestPlaces.title,
                imageUrl = bestPlaces.imageUrl
            )
        }
    )
}

data class Weather(val latitude: Double,
                   val longitude: Double,
                   val elevation: Int,
                   val time: String,
                   val temperature_2m_mean: Double,
                   val temperature_max24h_2m_mean: Double,
                   val temperature_min24h_2m_mean: Double,
                   val precipitation_mean: Double,
                   val showers_mean: Double,
                   val snowfall_mean: Double,
                   val snow_depth_mean: Double,
                   val cloud_cover_mean: Double,
                   val cloud_cover_low_mean: Double,
                   val shortwave_radiation_mean: Double,
                   val pressure_msl_mean: Double,
                   val wind_speed_10m_mean: Double,
                   val sunshine_duration_mean: Double,
                   val dew_point_2m_mean: Double)

fun WeatherResponse.toWeather(position: Int): Weather{
    return Weather(
        latitude = latitude,
        longitude = longitude,
        elevation = elevation,
        time = monthly.time[position],
        temperature_2m_mean = monthly.temperature[position],
        temperature_max24h_2m_mean = monthly.temperatureMax[position],
        temperature_min24h_2m_mean = monthly.temperatureMin[position],
        precipitation_mean = monthly.precipitation[position],
        showers_mean = monthly.showers[position],
        snowfall_mean = monthly.snowfall[position],
        snow_depth_mean = monthly.snowDepth[position],
        cloud_cover_mean = monthly.cloudCover[position],
        cloud_cover_low_mean = monthly.cloudCoverLow[position],
        shortwave_radiation_mean = monthly.shortwaveRadiation[position],
        pressure_msl_mean = monthly.pressureMsl[position],
        wind_speed_10m_mean = monthly.windSpeed[position],
        sunshine_duration_mean = monthly.sunshineDuration[position].div(3600.0),
        dew_point_2m_mean = monthly.dewPoint[position]
    )
}

fun WeatherTable.toWeather(): Weather{
    return Weather(
        latitude = latitude,
        longitude = longitude,
        elevation = elevation,
        time = date,
        temperature_2m_mean = temperature_2m_mean,
        temperature_max24h_2m_mean = temperature_max24h_2m_mean,
        temperature_min24h_2m_mean = temperature_min24h_2m_mean,
        precipitation_mean = precipitation_mean,
        showers_mean = showers_mean,
        snowfall_mean = snowfall_mean,
        snow_depth_mean = snow_depth_mean,
        cloud_cover_mean = cloud_cover_mean,
        cloud_cover_low_mean = cloud_cover_low_mean,
        shortwave_radiation_mean = shortwave_radiation_mean,
        pressure_msl_mean = pressure_msl_mean,
        wind_speed_10m_mean = wind_speed_10m_mean,
        sunshine_duration_mean = sunshine_duration_mean,
        dew_point_2m_mean = dew_point_2m_mean
    )
}

data class NearbyPlaces(val id: String,
                        val department: String,
                        val group: String,
                        val name: String,
                        val address: String?,
                        val latitude: Double,
                        val longitude: Double,
                        val distanceMeters: Int?,
                        val website: String?,
                        val openingHours: String?)


fun NearbyPlacesResponse.toNearbyPlaces(department: String, group: String): List<NearbyPlaces>{
    if (features.isEmpty()) return listOf()
    return features.mapNotNull { features ->
        val p = features.properties
        val id = p.placeId?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
        val name = p.name?.trim()?.takeIf { it.isNotEmpty() } ?: return@mapNotNull null
        val lat = p.lat ?: return@mapNotNull null
        val lon = p.lon ?: return@mapNotNull null
        NearbyPlaces(
            id = id,
            department = department,
            group = group,
            name = name,
            address = (p.addressLine2 ?: p.formatted)?.trim()?.takeIf { it.isNotEmpty() },
            latitude = lat,
            longitude = lon,
            distanceMeters = p.distance,
            website = p.website,
            openingHours = p.openingHours
        )
    }
}
