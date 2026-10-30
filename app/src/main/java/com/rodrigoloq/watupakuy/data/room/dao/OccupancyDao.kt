package com.rodrigoloq.watupakuy.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rodrigoloq.watupakuy.data.room.entities.occupancy.OccupancyTable

@Dao
interface OccupancyDao {

    @Insert
    suspend fun saveOccupancyData(occupancy: OccupancyTable): Long

    @Query("SELECT EXISTS(SELECT 1 FROM occupancy WHERE department = :department AND date = :date)")
    suspend fun doesOccupancyDataExist(department: String, date: String): Boolean

    @Query("SELECT * FROM occupancy WHERE department = :department AND date = :date")
    suspend fun getOccupancyData(department: String, date: String): OccupancyTable
}