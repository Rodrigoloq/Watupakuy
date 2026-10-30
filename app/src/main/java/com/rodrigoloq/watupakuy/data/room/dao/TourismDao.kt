package com.rodrigoloq.watupakuy.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rodrigoloq.watupakuy.data.room.entities.occupancy.OccupancyTable
import com.rodrigoloq.watupakuy.data.room.entities.tourism.TourismTable

@Dao
interface TourismDao {
    @Insert
    suspend fun saveTourismData(tourism: TourismTable): Long

    @Query("SELECT EXISTS(SELECT 1 FROM tourism WHERE department = :department AND date = :date)")
    suspend fun doesTourismDataExist(department: String, date: String): Boolean

    @Query("SELECT * FROM tourism WHERE department = :department AND date = :date")
    suspend fun getTourismData(department: String, date: String): TourismTable
}