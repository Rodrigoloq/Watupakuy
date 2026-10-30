package com.rodrigoloq.watupakuy.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodrigoloq.watupakuy.data.room.entities.tourism.TourismBestPlaceTable

@Dao
interface TourismBestPlaceDao {

    @Insert
    suspend fun saveTourismBestPlaceData(tourismBestPlace: TourismBestPlaceTable): Long

    @Query("SELECT * FROM tourism_best_places WHERE tourismId = :placeId")
    suspend fun getTourismBestPlaceData(placeId: Long): List<TourismBestPlaceTable>
}